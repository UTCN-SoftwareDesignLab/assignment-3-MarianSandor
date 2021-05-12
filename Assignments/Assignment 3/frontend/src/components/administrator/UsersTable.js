import React, { useContext } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { Table, Button } from "react-bootstrap";
import { UsersService } from "../../api/services/Users";
import { AuthenticationContext } from "../../contexts/Authentication";

const UsersTable = ({
  users,
  setUsers,
  setUpdateUsers,
  setUserToUpdate,
  setUpdateUserModal,
}) => {
  const [loggedUser, setUser] = useContext(AuthenticationContext);

  const deleteHandler = (user) => {
    let updatedUsers = users;

    UsersService.deleteUser(loggedUser, user.id)
      .then((value) => {
        let index = updatedUsers.indexOf(user);
        updatedUsers.splice(index);

        setUsers(updatedUsers);
        setUpdateUsers(true);
      })
      .catch((error) => {
        console.error("Could not delete book! " + error.message);
      });
  };

  return (
    <div>
      <Table striped bordered hover variant="dark">
        <thead>
          <tr>
            <th>Username</th>
            <th>Email</th>
            <th>Role</th>
          </tr>
        </thead>
        <tbody>
          {users.map((user) => (
            <tr key={user.id}>
              <td>{user.username}</td>
              <td>{user.email}</td>
              <td>{user.role}</td>
              <td>
                {user.role !== "ADMIN" && (
                  <Button
                    variant="primary"
                    type="button"
                    onClick={() => {
                      setUserToUpdate(user);
                      setUpdateUserModal(true);
                    }}
                  >
                    Update
                  </Button>
                )}
              </td>
              <td>
                {user.role !== "ADMIN" && (
                  <Button
                    variant="primary"
                    type="button"
                    onClick={() => deleteHandler(user)}
                  >
                    Delete
                  </Button>
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </Table>
    </div>
  );
};

export default UsersTable;
