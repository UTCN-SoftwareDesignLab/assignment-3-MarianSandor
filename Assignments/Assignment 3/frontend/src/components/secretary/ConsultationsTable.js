import React, { useContext } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { Table, Button } from "react-bootstrap";
import { ConsultationsService } from "../../api/services/Consultations";
import { AuthenticationContext } from "../../contexts/Authentication";

const ConsultationsTable = ({
  consultations,
  setConsultations,
  setUpdateConsultation,
  setConsultationsToUpdate,
  setUpdateConsultationsModal,
}) => {
  const [user, setUser] = useContext(AuthenticationContext);

  const deleteHandler = (consultation) => {
    let updatedConsultations = consultations;

    ConsultationsService.deleteConsultation(user, consultation.id)
      .then((value) => {
        let index = updatedConsultations.indexOf(consultation);
        updatedConsultations.splice(index);

        setConsultations(updatedConsultations);
        setUpdateConsultation(true);
      })
      .catch((error) => {
        console.error("Could not delete consultation! " + error.message);
      });
  };

  return (
    <div>
      <Table striped bordered hover variant="dark">
        <thead>
          <tr>
            <th>Doctor</th>
            <th>Patient</th>
            <th>Date</th>
            <th>Description</th>
          </tr>
        </thead>
        <tbody>
          {consultations.map((consultation) => (
            <tr key={consultation.id}>
              <td>{consultation.user_name}</td>
              <td>{consultation.patient_name}</td>
              <td>{consultation.time}</td>
              <td>{consultation.description}</td>
              <td>
                <Button
                  variant="primary"
                  type="button"
                  onClick={() => deleteHandler(consultation)}
                >
                  Delete
                </Button>
              </td>
            </tr>
          ))}
        </tbody>
      </Table>
    </div>
  );
};

export default ConsultationsTable;
