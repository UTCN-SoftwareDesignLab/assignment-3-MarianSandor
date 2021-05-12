import React, { useState, useContext } from "react";
import ReactDom from "react-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import { Button, Form, Row, Col } from "react-bootstrap";
import style from "../components.module.css";
import { UsersService } from "../../api/services/Users";
import { AuthenticationContext } from "../../contexts/Authentication";

export default function Modal({
  updateUserModal,
  setUpdateUserModal,
  setUpdateUsers,
  userToUpdate,
}) {
  const [user, setUser] = useContext(AuthenticationContext);
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [role, setRole] = useState("SECRETARY");

  if (!updateUserModal) return null;

  const initializeFields = () => {
    setUsername("");
    setEmail("");
    setRole(userToUpdate.role);
  };

  const closeHandler = () => {
    initializeFields();

    setUpdateUserModal(false);
  };

  const updateHandler = (e) => {
    e.preventDefault();

    let data = {
      username: username === "" ? userToUpdate.username : username,
      email: email === "" ? userToUpdate.email : email,
      role: role === "" ? userToUpdate.role : role,
    };

    UsersService.updateUser(user, userToUpdate.id, data)
      .then((value) => {
        userToUpdate = value;

        initializeFields();
        setUpdateUserModal(false);
        setUpdateUsers(true);
      })
      .catch((error) => {
        console.error("Failed to update user! " + error.message);
      });
  };

  return ReactDom.createPortal(
    <>
      <div className={style.overlay} />
      <div className={style.userModal}>
        <Form onSubmit={updateHandler}>
          <Form.Group controlId="formBasicUsername">
            <Form.Label className="text-center" style={{ width: "100%" }}>
              Username
            </Form.Label>
            <Form.Control
              type="username"
              placeholder={userToUpdate.username}
              value={username}
              onChange={(e) => setUsername(e.target.value)}
            />
          </Form.Group>

          <Form.Group controlId="formBasicEmail">
            <Form.Label className="text-center" style={{ width: "100%" }}>
              Email
            </Form.Label>
            <Form.Control
              type="email"
              placeholder={userToUpdate.email}
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
          </Form.Group>

          <Form.Group as={Row} controlId="formBasicRadio">
            <Form.Label as="legend" column sm={2}>
              Role
            </Form.Label>
            <Col sm={10}>
              <Form.Check
                type="radio"
                label="Admin"
                name="role"
                id="ADMIN"
                onChange={() => setRole("ADMIN")}
                defaultChecked={userToUpdate.role === "ADMIN"}
              />
              <Form.Check
                type="radio"
                label="Secretary"
                name="role"
                id="SECRETARY"
                onChange={() => setRole("SECRETARY")}
                defaultChecked={userToUpdate.role === "SECRETARY"}
              />
              <Form.Check
                type="radio"
                label="Doctor"
                name="role"
                id="DOCTOR"
                onChange={() => setRole("DOCTOR")}
                defaultChecked={userToUpdate.role === "DOCTOR"}
              />
            </Col>
          </Form.Group>

          <Button
            variant="primary"
            type="submit"
            className="text-center"
            style={{ width: "100%" }}
          >
            Update
          </Button>
        </Form>
        <button className={style.closeButton} onClick={closeHandler}>
          X
        </button>
      </div>
    </>,
    document.getElementById("portal")
  );
}
