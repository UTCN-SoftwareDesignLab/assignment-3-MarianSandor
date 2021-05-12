import React, { useState, useContext } from "react";
import ReactDom from "react-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import { Button, Form } from "react-bootstrap";
import style from "../components.module.css";
import { AuthenticationContext } from "../../contexts/Authentication";
import { PatientsService } from "../../api/services/Patients";

export default function Modal({
  updatePatientModal,
  setUpdatePatientModal,
  setUpdatePatients,
  patientToUpdate,
}) {
  const [user, setUser] = useContext(AuthenticationContext);
  const [name, setName] = useState("");
  const [cardNumber, setCardNumber] = useState("");
  const [cnp, setCnp] = useState("");
  const [address, setAddress] = useState("");
  const [birthDate, setBirthDate] = useState("");

  if (!updatePatientModal) return null;

  const initializeFields = () => {
    setName("");
    setCardNumber("");
    setCnp("");
    setAddress("");
    setBirthDate("");
  };

  const closeHandler = () => {
    initializeFields();

    setUpdatePatientModal(false);
  };

  const updateHandler = (e) => {
    e.preventDefault();

    let data = {
      name: name === "" ? patientToUpdate.name : name,
      cardNumber: cardNumber === "" ? patientToUpdate.cardNumber : cardNumber,
      cnp: cnp === "" ? patientToUpdate.cnp : cnp,
      address: address === "" ? patientToUpdate.address : address,
      birthDate: patientToUpdate.birthDate,
    };

    PatientsService.updatePatient(user, patientToUpdate.id, data)
      .then((value) => {
        patientToUpdate = value;

        setUpdatePatientModal(false);
        setUpdatePatients(true);
        initializeFields();
      })
      .catch((error) => {
        console.error("Failed to update patient! " + error.message);
      });
  };

  return ReactDom.createPortal(
    <>
      <div className={style.overlay} />
      <div className={style.patientModal}>
        <Form onSubmit={updateHandler}>
          <Form.Group controlId="formBasicName">
            <Form.Label className="text-center" style={{ width: "100%" }}>
              Name
            </Form.Label>
            <Form.Control
              type="text"
              placeholder={patientToUpdate.name}
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
              placeholder={patientToUpdate.cardNumber}
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
              placeholder={patientToUpdate.cnp}
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
              placeholder={patientToUpdate.address}
              value={address}
              onChange={(e) => setAddress(e.target.value)}
            />
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
