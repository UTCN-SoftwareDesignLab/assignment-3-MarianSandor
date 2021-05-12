import React, { useState, useContext } from "react";
import style from "../components.module.css";
import "bootstrap/dist/css/bootstrap.min.css";
import { Form, Button } from "react-bootstrap";
import { AuthenticationContext } from "../../contexts/Authentication";
import { useHistory } from "react-router-dom";
import { AuthenticationService } from "../../api/services/Authentication";
import { WebSocketConnection } from "../../api/WebSocketConnection";
import { MessageContext, MessageModalContext } from "../../contexts/Message";

const LoginForm = ({ setLogin }) => {
  let history = useHistory();

  const [user, setUser] = useContext(AuthenticationContext);
  const [message, setMessage] = useContext(MessageContext);
  const [showAlertModal, setShowAlertModal] = useContext(MessageModalContext);
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const loginHandler = async (e) => {
    e.preventDefault();

    let loginRequest = {
      username: username,
      password: password,
    };

    AuthenticationService.login(loginRequest)
      .then((value) => {
        if (value && value.token) {
          setUser(value);
          if (value.roles[0] === "ADMIN") history.push("/administrator");
          else if (value.roles[0] === "SECRETARY") {
            history.push("/secretary");
            WebSocketConnection.connect(
              value,
              false,
              setMessage,
              setShowAlertModal
            );
          } else if (value.roles[0] === "DOCTOR") {
            history.push("/doctor");
            WebSocketConnection.connect(
              value,
              true,
              setMessage,
              setShowAlertModal
            );
          }
        }
      })
      .catch((error) => {
        console.error("Failed to login! " + error.message);
      });

    setUsername("");
    setPassword("");
  };

  return (
    <div className={style.userModal}>
      <Form onSubmit={loginHandler}>
        <Form.Group controlId="formBasicUsername">
          <Form.Label>Username</Form.Label>
          <Form.Control
            type="username"
            placeholder="username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
        </Form.Group>

        <Form.Group controlId="formBasicPassword">
          <Form.Label>Password</Form.Label>
          <Form.Control
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </Form.Group>

        <Button variant="primary" type="submit">
          Login
        </Button>
      </Form>

      <h6>Don't have an account yet?</h6>

      <Button variant="primary" type="button" onClick={() => setLogin(false)}>
        Register
      </Button>
    </div>
  );
};

export default LoginForm;
