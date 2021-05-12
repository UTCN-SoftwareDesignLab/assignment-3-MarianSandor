import React, { useState, useContext } from "react";
import { Navbar, Button, Nav, Form, FormControl } from "react-bootstrap";
import { AuthenticationContext } from "../../contexts/Authentication";

const UsersSearchBar = ({ setSearchedUser, setAddUserModal }) => {
  const [user, setUser] = useContext(AuthenticationContext);
  const [search, setSearch] = useState("");

  const updateSearch = (e) => {
    setSearch(e.target.value);
  };

  const searchHandler = (e) => {
    e.preventDefault();

    setSearchedUser(search);
    setSearch("");
  };

  return (
    <div>
      <Navbar sticky="top" bg="light" expand="lg">
        <Navbar.Brand>Clinic</Navbar.Brand>
        <Nav
          className="justify-content-center"
          style={{ marginLeft: "25%" }}
        ></Nav>
        <Form inline onSubmit={searchHandler}>
          <FormControl
            type="text"
            placeholder="search by Username/Email"
            value={search}
            onChange={updateSearch}
            className="mr-sm-2"
            style={{ width: "25rem" }}
          />
          <Button variant="outline-success" type="submit">
            Search
          </Button>
        </Form>
        <Button
          style={{ marginLeft: "10px" }}
          onClick={() => setAddUserModal(true)}
        >
          Add
        </Button>
        <Button style={{ marginLeft: "21%" }} onClick={() => setUser({})}>
          Logout
        </Button>
      </Navbar>
    </div>
  );
};

export default UsersSearchBar;
