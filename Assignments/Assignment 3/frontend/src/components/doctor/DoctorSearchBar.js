import React, { useState, useContext } from "react";
import { Navbar, Button, Nav, Form, FormControl } from "react-bootstrap";
import { AuthenticationContext } from "../../contexts/Authentication";

const DoctorSearchBar = () => {
  const [user, setUser] = useContext(AuthenticationContext);

  return (
    <div>
      <Navbar sticky="top" bg="light" expand="lg">
        <Navbar.Brand>Clinic</Navbar.Brand>
        <Nav
          className="justify-content-center"
          style={{ marginLeft: "25%" }}
        ></Nav>
        <Button style={{ marginLeft: "60%" }} onClick={() => setUser({})}>
          Logout
        </Button>
      </Navbar>
    </div>
  );
};

export default DoctorSearchBar;
