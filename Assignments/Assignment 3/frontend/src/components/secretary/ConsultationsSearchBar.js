import React, { useState, useContext } from "react";
import { Navbar, Button, Nav } from "react-bootstrap";
import { AuthenticationContext } from "../../contexts/Authentication";
import { WebSocketConnection } from "../../api/WebSocketConnection";

const ConsultationsSearchBar = ({ setPatientsConsultationsUI }) => {
  const [user, setUser] = useContext(AuthenticationContext);

  return (
    <div>
      <Navbar sticky="top" bg="light" expand="lg">
        <Navbar.Brand>Clinic</Navbar.Brand>
        <Nav
          className="justify-content-center"
          style={{ marginLeft: "10%" }}
        ></Nav>
        <Button
          style={{ marginRight: "10%" }}
          onClick={() => setPatientsConsultationsUI(true)}
        >
          Patients
        </Button>
        <Button
          style={{ marginLeft: "60%" }}
          onClick={() => {
            WebSocketConnection.disconnect();
            setUser({});
          }}
        >
          Logout
        </Button>
      </Navbar>
    </div>
  );
};

export default ConsultationsSearchBar;
