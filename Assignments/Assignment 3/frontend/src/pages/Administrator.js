import React, { useState, useEffect, useContext } from "react";
import UsersTable from "../components/administrator/UsersTable";
import UsersSearchBar from "../components/administrator/UsersSearchBar";
import AddUser from "../components/modals/AddUser";
import UpdateUser from "../components/modals/UpdateUser";
import { AuthenticationContext } from "../contexts/Authentication";
import { UsersService } from "../api/services/Users";

function Administrator() {
  const [user, setUser] = useContext(AuthenticationContext);
  const [usersBooksUI, setUsersBooksUI] = useState(true);
  const [searchedBook, setSearchedBook] = useState("");
  const [users, setUsers] = useState([]);
  const [searchedUser, setSearchedUser] = useState("");
  const [addBookModal, setAddBookModal] = useState(false);
  const [addUserModal, setAddUserModal] = useState(false);
  const [updateBookModal, setUpdateBookModal] = useState(false);
  const [updateUserModal, setUpdateUserModal] = useState(false);
  const [bookToUpdate, setBookToUpdate] = useState({});
  const [userToUpdate, setUserToUpdate] = useState({});
  const [updateBooks, setUpdateBooks] = useState(false);
  const [updateUsers, setUpdateUsers] = useState(false);

  const setAllUsers = async () => {
    UsersService.getAll(user)
      .then((value) => {
        setUsers(value);
      })
      .catch((error) => {
        console.error("Could not fetch users! " + error.message);
      });
  };

  const setSearchedUsers = async () => {
    UsersService.searchUser(user, searchedUser)
      .then((value) => {
        setUsers(value);
      })
      .catch((error) => {
        console.error("Could not fetch users! " + error.message);
      });
  };

  useEffect(async () => {
    if (updateUsers) {
      setUsers(users);
      setUpdateUsers(false);
    }

    if (searchedUser === "") {
      setAllUsers();
    } else {
      setSearchedUsers();
      setSearchedUser();
    }
  }, [searchedUser, user, updateUsers]);

  return (
    <>
      <div>
        <UsersSearchBar
          setUsersBooksUI={setUsersBooksUI}
          setSearchedUser={setSearchedUser}
          setAddUserModal={setAddUserModal}
        ></UsersSearchBar>
        <UsersTable
          users={users}
          setUsers={setUsers}
          setUpdateUsers={setUpdateUsers}
          setUserToUpdate={setUserToUpdate}
          setUpdateUserModal={setUpdateUserModal}
        ></UsersTable>
      </div>
      <AddUser
        addUserModal={addUserModal}
        setAddUserModal={setAddUserModal}
        setUpdateUsers={setUpdateUsers}
      />
      <UpdateUser
        updateUserModal={updateUserModal}
        setUpdateUserModal={setUpdateUserModal}
        setUpdateUsers={setUpdateUsers}
        userToUpdate={userToUpdate}
      />
    </>
  );
}

export default Administrator;
