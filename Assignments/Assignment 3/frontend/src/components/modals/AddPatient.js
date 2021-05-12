import React, { useState, useContext } from "react";
import ReactDom from "react-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import { Button, Form } from "react-bootstrap";
import style from "../components.module.css";
import { AuthenticationContext } from "../../contexts/Authentication";
import { PatientsService } from "../../api/services/Patients";

export default function Modal({
  addPatientModal,
  setAddPatientModal,
  setUpdatePatients,
}) {
  const [user, setUser] = useContext(AuthenticationContext);
  const [name, setName] = useState("");
  const [cardNumber, setCardNumber] = useState("");
  const [cnp, setCnp] = useState("");
  const [address, setAddress] = useState("");
  const [birthDate, setBirthDate] = useState("");

  if (!addPatientModal) return null;

  const initializeFields = () => {
    setName("");
    setCardNumber("");
    setCnp("");
    setAddress("");
    setBirthDate("");
  };

  const closeHandler = () => {
    initializeFields();

    setAddPatientModal(false);
  };

  const addHandler = (e) => {
    e.preventDefault();

    var date = birthDate.split("-");
    let data = {
      name: name,
      cardNumber: cardNumber,
      cnp: cnp,
      address: address,
      birthDate: date[2] + "-" + date[1] + "-" + date[0],
    };

    PatientsService.addPatient(user, data).catch((error) => {
      console.error("Failed to add patient! " + error.message);
    });

    setAddPatientModal(false);
    setUpdatePatients(true);
    initializeFields();
  };

  return ReactDom.createPortal(
    <>
      <div className={style.overlay} />
      <div className={style.patientModal}>
        <Form onSubmit={addHandler}>
          <Form.Group controlId="formBasicName">
            <Form.Label className="text-center" style={{ width: "100%" }}>
              Name
            </Form.Label>
            <Form.Control
              type="text"
              placeholder="name"
              value={name}
              onChange={(e) => setName(e.target.value)}
            />
          </Form.Group>

          <Form.Group controlId="formBasicCardNumber">
            <Form.Label className="text-center" style={{ width: "100%" }}>
              CardNumber
            </Form.Label>
            <Form.Control
              type="text"
              placeholder="card number"
              value={cardNumber}
              onChange={(e) => setCardNumber(e.target.value)}
            />
          </Form.Group>

          <Form.Group controlId="formBasicCnp">
            <Form.Label className="text-center" style={{ width: "100%" }}>
              Cnp
            </Form.Label>
            <Form.Control
              type="text"
              placeholder="cnp"
              value={cnp}
              onChange={(e) => setCnp(e.target.value)}
            />
          </Form.Group>

          <Form.Group controlId="formBasicAddress">
            <Form.Label className="text-center" style={{ width: "100%" }}>
              Address
            </Form.Label>
            <Form.Control
              type="text"
              placeholder="address"
              value={address}
              onChange={(e) => setAddress(e.target.value)}
            />
          </Form.Group>

          <Form.Group controlId="formBasicDate">
            <Form.Label className="text-center" style={{ width: "100%" }}>
              Date of Birth
            </Form.Label>
            <Form.Control
              type="date"
              placeholder="date of birth"
              value={birthDate}
              onChange={(e) => setBirthDate(e.target.value)}
            />
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
