import React, { useContext } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { Table, Button } from "react-bootstrap";

const PatientsTable = ({
  patients,
  setPatientToUpdate,
  setUpdatePatientModal,
  setPatientsConsultationsUI,
  setSearchedConsultation,
  setAddConsultationModal,
  setPatientId,
}) => {
  return (
    <div>
      <Table striped bordered hover variant="dark">
        <thead>
          <tr>
            <th>Name</th>
            <th>Card Number</th>
            <th>CNP</th>
            <th>Date of Birth</th>
            <th>Address</th>
          </tr>
        </thead>
        <tbody>
          {patients.map((patient) => (
            <tr key={patient.id}>
              <td>{patient.name}</td>
              <td>{patient.cardNumber}</td>
              <td>{patient.cnp}</td>
              <td>{patient.birthDate}</td>
              <td>{patient.address}</td>
              <td>
                <Button
                  variant="primary"
                  type="button"
                  onClick={() => {
                    setPatientToUpdate(patient);
                    setUpdatePatientModal(true);
                  }}
                >
                  Update
                </Button>
              </td>
              <td>
                <Button
                  variant="primary"
                  type="button"
                  onClick={() => {
                    setPatientId(patient);
                    setAddConsultationModal(true);
                  }}
                >
                  Add Consultation
                </Button>
              </td>
              <td>
                <Button
                  variant="primary"
                  type="button"
                  onClick={() => {
                    setSearchedConsultation(patient.id);
                    setPatientsConsultationsUI(false);
                  }}
                >
                  Consultations
                </Button>
              </td>
            </tr>
          ))}
        </tbody>
      </Table>
    </div>
  );
};

export default PatientsTable;
