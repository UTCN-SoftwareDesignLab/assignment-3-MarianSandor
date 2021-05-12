import React, { useState } from "react";
import style from "../components.module.css";
import "bootstrap/dist/css/bootstrap.min.css";
import { Form, Button } from "react-bootstrap";
import { AuthenticationService } from "../../api/services/Authentication";

const SignUpForm = ({ setLogin }) => {
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const signUpHandler = async (e) => {
    e.preventDefault();

    let signUpRequest = {
      username: username,
      email: email,
      password: password,
    };

    AuthenticationService.register(signUpRequest)
      .then((value) => {
        setLogin(true);
      })
      .catch((error) => {
        console.error("Failed to signUp! " + error.message);
      });

    setUsername("");
    setEmail("");
    setPassword("");
  };

  return (
    <div className={style.userModal}>
      <Form onSubmit={signUpHandler}>
        <Form.Group controlId="formBasicUsername">
          <Form.Label>Username</Form.Label>
          <Form.Control
            type="username"
            placeholder="username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
        </Form.Group>

        <Form.Group controlId="formBasicEmail">
          <Form.Label>Email</Form.Label>
          <Form.Control
            type="email"
            placeholder="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
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
          SignUp
        </Button>
      </Form>
    </div>
  );
};

export default SignUpForm;
