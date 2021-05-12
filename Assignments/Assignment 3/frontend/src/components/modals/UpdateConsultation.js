import React, { useState, useContext } from "react";
import ReactDom from "react-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import { Button, Form, Row, Col } from "react-bootstrap";
import style from "../components.module.css";
import { ConsultationsService } from "../../api/services/Consultations";
import { AuthenticationContext } from "../../contexts/Authentication";

export default function Modal({
  updateConsultationModal,
  setUpdateConsultationModal,
  setUpdateConsultations,
  consultationToUpdate,
}) {
  const [user, setUser] = useContext(AuthenticationContext);
  const [description, setDescription] = useState("");

  if (!updateConsultationModal) return null;

  const initializeFields = () => {
    setDescription(consultationToUpdate.description);
  };

  const closeHandler = () => {
    initializeFields();

    setUpdateConsultationModal(false);
  };

  const updateHandler = (e) => {
    e.preventDefault();

    ConsultationsService.updateDescription(
      user,
      consultationToUpdate.id,
      description
    )
      .then((value) => {
        consultationToUpdate = value;

        initializeFields();
        setUpdateConsultationModal(false);
        setUpdateConsultations(true);
      })
      .catch((error) => {
        console.error("Failed to update consultation! " + error.message);
      });
  };

  return ReactDom.createPortal(
    <>
      <div className={style.overlay} />
      <div className={style.userModal}>
        <Form onSubmit={updateHandler}>
          <Form.Group controlId="formBasicUsername">
            <Form.Label className="text-center" style={{ width: "100%" }}>
              Doctor
            </Form.Label>
            <Form.Control
              type="text"
              placeholder={consultationToUpdate.user_name}
              readOnly
            />
          </Form.Group>

          <Form.Group controlId="formBasicEmail">
            <Form.Label className="text-center" style={{ width: "100%" }}>
              Patient
            </Form.Label>
            <Form.Control
              type="text"
              placeholder={consultationToUpdate.patient_name}
              readOnly
            />
          </Form.Group>

          <Form.Group as={Row} controlId="formBasicRadio">
            <Form.Label className="text-center" style={{ width: "100%" }}>
              Date
            </Form.Label>
            <Form.Control
              type="text"
              placeholder={consultationToUpdate.time}
              readOnly
            />
          </Form.Group>

          <Form.Group controlId="formBasicAddress">
            <Form.Label className="text-center" style={{ width: "100%" }}>
              Description
            </Form.Label>
            <Form.Control
              type="text"
              placeholder={consultationToUpdate.description}
              value={description}
              onChange={(e) => setDescription(e.target.value)}
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
