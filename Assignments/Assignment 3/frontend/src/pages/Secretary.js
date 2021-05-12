import React, { useState, useEffect, useContext } from "react";
import PatientsTable from "../components/secretary/PatientsTable";
import PatientsSearchBar from "../components/secretary/PatientsSearchBar";
import ConsultationsTable from "../components/secretary/ConsultationsTable";
import ConsultationsSearchBar from "../components/secretary/ConsultationsSearchBar";
import AddPatient from "../components/modals/AddPatient";
import UpdatePatient from "../components/modals/UpdatePatient";
import AddConsultation from "../components/modals/AddConsultation";
import { AuthenticationContext } from "../contexts/Authentication";
import { PatientsService } from "../api/services/Patients";
import { ConsultationsService } from "../api/services/Consultations";

function Secretary() {
  const [user, setUser] = useContext(AuthenticationContext);
  const [patientsConsultationsUI, setPatientsConsultationsUI] = useState(true);
  const [patients, setPatients] = useState([]);
  const [updatePatients, setUpdatePatients] = useState(false);
  const [searchedPatient, setSearchedPatient] = useState("");
  const [consultations, setConsultations] = useState([]);
  const [updateConsultations, setUpdateConsultations] = useState(false);
  const [searchedConsultation, setSearchedConsultation] = useState("");
  const [addPatientModal, setAddPatientModal] = useState(false);
  const [addConsultationModal, setAddConsultationModal] = useState(false);
  const [updatePatientModal, setUpdatePatientModal] = useState(false);
  const [patientToUpdate, setPatientToUpdate] = useState({});
  const [consultationToUpdate, setConsultationToUpdate] = useState({});
  const [patientId, setPatientId] = useState("");

  const setAllPatients = async () => {
    PatientsService.getAll(user)
      .then((value) => {
        setPatients(value);
      })
      .catch((error) => {
        console.error("Could not fetch patients! " + error.message);
      });
  };

  const setSearchedPatients = async () => {
    PatientsService.searchPatient(user, searchedPatient)
      .then((value) => {
        setPatients(value);
      })
      .catch((error) => {
        console.error("Could not fetch patients! " + error.message);
      });
  };

  const setAllConsultations = async () => {
    ConsultationsService.getAll(user)
      .then((value) => {
        setConsultations(value);
      })
      .catch((error) => {
        console.error("Could not fetch consultations! " + error.message);
      });
  };

  const setSearchedConsultations = async () => {
    PatientsService.getConsultationsForPatient(user, searchedConsultation)
      .then((value) => {
        setConsultations(value);
      })
      .catch((error) => {
        console.error("Could not fetch consultations! " + error.message);
      });
  };

  useEffect(async () => {
    if (updatePatients) {
      setPatients(patients);
      setUpdatePatients(false);
    }
    if (updateConsultations) {
      setConsultations(consultations);
      setUpdateConsultations(false);
    }

    if (patientsConsultationsUI) {
      if (searchedPatient === "") {
        setAllPatients();
      } else {
        setSearchedPatients();
      }
      setSearchedConsultation("");
    } else {
      if (searchedConsultation === "") {
        setAllConsultations();
      } else {
        setSearchedConsultations();
      }
      setSearchedPatient("");
    }
  }, [
    searchedConsultation,
    searchedPatient,
    patientsConsultationsUI,
    user,
    updateConsultations,
    updatePatients,
  ]);

  return (
    <>
      <div>
        {patientsConsultationsUI ? (
          <div>
            <PatientsSearchBar
              setPatientsConsultationsUI={setPatientsConsultationsUI}
              setSearchedPatient={setSearchedPatient}
              setAddPatientModal={setAddPatientModal}
            ></PatientsSearchBar>
            <PatientsTable
              patients={patients}
              setPatientToUpdate={setPatientToUpdate}
              setUpdatePatientModal={setUpdatePatientModal}
              setSearchedConsultation={setSearchedConsultation}
              setPatientsConsultationsUI={setPatientsConsultationsUI}
              setAddConsultationModal={setAddConsultationModal}
              setPatientId={setPatientId}
            ></PatientsTable>
          </div>
        ) : (
          <div>
            <ConsultationsSearchBar
              setPatientsConsultationsUI={setPatientsConsultationsUI}
            ></ConsultationsSearchBar>
            <ConsultationsTable
              consultations={consultations}
              setConsultations={setConsultations}
              setUpdateConsultations={setUpdateConsultations}
              setConsultationToUpdate={setConsultationToUpdate}
            ></ConsultationsTable>
          </div>
        )}
      </div>
      <AddPatient
        addPatientModal={addPatientModal}
        setAddPatientModal={setAddPatientModal}
        setUpdatePatients={setUpdatePatients}
      />
      <UpdatePatient
        updatePatientModal={updatePatientModal}
        setUpdatePatientModal={setUpdatePatientModal}
        setUpdatePatients={setUpdatePatients}
        patientToUpdate={patientToUpdate}
      />
      <AddConsultation
        addConsultationModal={addConsultationModal}
        setAddConsultationModal={setAddConsultationModal}
        patientId={patientId}
      />
    </>
  );
}

export default Secretary;
