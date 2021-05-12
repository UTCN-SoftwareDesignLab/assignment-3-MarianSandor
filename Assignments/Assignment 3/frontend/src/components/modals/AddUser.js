import React, { useState, useContext } from "react";
import ReactDom from "react-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import { Button, Form, Row, Col } from "react-bootstrap";
import style from "../components.module.css";
import { UsersService } from "../../api/services/Users";
import { AuthenticationContext } from "../../contexts/Authentication";

export default function Modal({
  addUserModal,
  setAddUserModal,
  setUpdateUsers,
}) {
  const [user, setUser] = useContext(AuthenticationContext);
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [role, setRole] = useState("SECRETARY");

  if (!addUserModal) return null;

  const initializeFields = () => {
    setUsername("");
    setEmail("");
    setPassword("");
    setRole("SECRETARY");
  };

  const closeHandler = () => {
    initializeFields();

    setAddUserModal(false);
  };

  const addHandler = (e) => {
    e.preventDefault();

    let data = {
      username: username,
      email: email,
      password: password,
      role: role,
    };

    UsersService.addUser(user, data).catch((error) => {
      console.error("Failed to add user! " + error.message);
    });

    setAddUserModal(false);
    setUpdateUsers(true);
    initializeFields();
  };

  return ReactDom.createPortal(
    <>
      <div className={style.overlay} />
      <div className={style.userModal}>
        <Form onSubmit={addHandler}>
          <Form.Group controlId="formBasicUsername">
            <Form.Label className="text-center" style={{ width: "100%" }}>
              Username
            </Form.Label>
            <Form.Control
              type="username"
              placeholder="username"
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
              placeholder="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
          </Form.Group>

          <Form.Group controlId="formBasicPassword">
            <Form.Label className="text-center" style={{ width: "100%" }}>
              Password
            </Form.Label>
            <Form.Control
              type="password"
              placeholder="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
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
              />
              <Form.Check
                type="radio"
                label="Secretary"
                name="role"
                id="SECRETARY"
                onChange={() => setRole("SECRETARY")}
                defaultChecked
              />
              <Form.Check
                type="radio"
                label="Doctor"
                name="role"
                id="DOCTOR"
                onChange={() => setRole("DOCTOR")}
              />
            </Col>
          </Form.Group>

          <Button
            variant="primary"
            type="submit"
            className="text-center"
            style={{ width: "100%" }}
          >
            Add
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
