import React, { useState, useContext } from "react";
import ReactDom from "react-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import { Col, Button, Form } from "react-bootstrap";
import style from "../components.module.css";
import { AuthenticationContext } from "../../contexts/Authentication";
import { ConsultationsService } from "../../api/services/Consultations";
import { UsersService } from "../../api/services/Users";
import { WebSocketConnection } from "../../api/WebSocketConnection";

export default function Modal({
  addConsultationModal,
  setAddConsultationModal,
  patientId,
}) {
  const [user, setUser] = useContext(AuthenticationContext);
  const [userId, setUserId] = useState({});
  const [date, setDate] = useState("");
  const [hour, setHour] = useState(0);
  const [doctors, setDoctors] = useState([]);

  if (!addConsultationModal) return null;

  const initializeFields = () => {
    setUserId({});
    setDate("");
    setDoctors([]);
    setHour(0);
  };

  const closeHandler = () => {
    initializeFields();

    setAddConsultationModal(false);
  };

  const dateChangeHandler = async (d) => {
    setDate(d);
    var dateF = d.split("-");
    var data = dateF[2] + "-" + dateF[1] + "-" + dateF[0];

    UsersService.getDoctorsAvailable(user, data)
      .then((value) => {
        setDoctors(value);
      })
      .catch((error) => {
        console.error("Could not fetch doctors! " + error.message);
      });
  };

  const addHandler = (e) => {
    e.preventDefault();

    var dateF = date.split("-");
    var h = (hour < 10 ? "0" + hour : hour) + ":00";
    let data = {
      user_id: doctors[hour][userId].id,
      patient_id: patientId.id,
      time: dateF[2] + "-" + dateF[1] + "-" + dateF[0] + " " + h,
    };

    ConsultationsService.addConsultation(user, data)
      .then((value) => {
        let message = {
          doctorName: doctors[hour][userId].name,
          patientName: patientId.name,
          date: dateF[2] + "-" + dateF[1] + "-" + dateF[0] + " " + h,
        };
        WebSocketConnection.sendMessage(user, message);
      })
      .catch((error) => {
        console.error("Failed to add consultation! " + error.message);
      });

    setAddConsultationModal(false);
    initializeFields();
  };

  return ReactDom.createPortal(
    <>
      <div className={style.overlay} />
      <div className={style.consultationModal}>
        <Form onSubmit={addHandler}>
          <Form.Group controlId="formBasicDate">
            <Form.Label className="text-center" style={{ width: "100%" }}>
              Choose a Date
            </Form.Label>
            <Form.Control
              type="date"
              value={date}
              onChange={(e) => dateChangeHandler(e.target.value)}
            />
          </Form.Group>

          <Form.Group as={Col} controlId="formGridState">
            <Form.Label className="text-center" style={{ width: "100%" }}>
              Choose an hour
            </Form.Label>
            <Form.Control
              as="select"
              onChange={(e) => {
                setHour(e.target.value);
              }}
            >
              {doctors.map((doctor) => (
                <option
                  value={doctors.indexOf(doctor)}
                  key={doctors.indexOf(doctor)}
                >
                  {(doctors.indexOf(doctor) < 10
                    ? "0" + doctors.indexOf(doctor)
                    : doctors.indexOf(doctor)) + ":00"}
                </option>
              ))}
            </Form.Control>
          </Form.Group>

          <Form.Group as={Col} controlId="formGridState2">
            <Form.Label className="text-center" style={{ width: "100%" }}>
              Choose a doctor
            </Form.Label>
            <Form.Control
              as="select"
              onChange={(e) => {
                setUserId(e.target.value);
              }}
            >
              <option value={{}}></option>
              {Object.keys(doctors).length === 0 ? (
                <option></option>
              ) : (
                doctors[hour].map((doctor) => (
                  <option
                    value={doctors[hour].indexOf(doctor)}
                    key={doctor.name}
                  >
                    {doctor.name}
                  </option>
                ))
              )}
            </Form.Control>
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
