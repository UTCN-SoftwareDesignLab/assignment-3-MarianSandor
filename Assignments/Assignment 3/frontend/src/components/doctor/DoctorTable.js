import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { Table, Button } from "react-bootstrap";

const DoctorTable = ({
  consultations,
  setConsultationToUpdate,
  setUpdateConsultationModal,
}) => {
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
                  onClick={() => {
                    setConsultationToUpdate(consultation);
                    setUpdateConsultationModal(true);
                  }}
                >
                  Add Description
                </Button>
              </td>
            </tr>
          ))}
        </tbody>
      </Table>
    </div>
  );
};

export default DoctorTable;
