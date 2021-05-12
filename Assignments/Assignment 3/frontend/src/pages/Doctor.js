import React, { useState, useEffect, useContext } from "react";
import { AuthenticationContext } from "../contexts/Authentication";
import DoctorSearchBar from "../components/doctor/DoctorSearchBar";
import DoctorTable from "../components/doctor/DoctorTable";
import { UsersService } from "../api/services/Users";
import UpdateConsultation from "../components/modals/UpdateConsultation";
import ShowAlert from "../components/modals/ShowAlert";
import { MessageContext, MessageModalContext } from "../contexts/Message";

function Doctor() {
  const [user, setUser] = useContext(AuthenticationContext);
  const [message, setMessage] = useContext(MessageContext);
  const [showAlertModal, setShowAlertModal] = useContext(MessageModalContext);
  const [consultations, setConsultations] = useState([]);
  const [updateConsultations, setUpdateConsultations] = useState(false);
  const [updateConsultationModal, setUpdateConsultationModal] = useState(false);
  const [consultationToUpdate, setConsultationToUpdate] = useState({});

  const setAllConsultations = async () => {
    UsersService.getConsultationsForUser(user)
      .then((value) => {
        setConsultations(value);
      })
      .catch((error) => {
        console.error("Could not fetch consultations! " + error.message);
      });
  };

  useEffect(async () => {
    setAllConsultations();
    setUpdateConsultations(false);
  }, [updateConsultations, message]);

  return (
    <>
      <div>
        <DoctorSearchBar></DoctorSearchBar>
        <DoctorTable
          consultations={consultations}
          setConsultationToUpdate={setConsultationToUpdate}
          setUpdateConsultationModal={setUpdateConsultationModal}
        ></DoctorTable>
      </div>
      <div>
        <UpdateConsultation
          updateConsultationModal={updateConsultationModal}
          setUpdateConsultationModal={setUpdateConsultationModal}
          setUpdateConsultations={setUpdateConsultations}
          consultationToUpdate={consultationToUpdate}
        />
      </div>
      <ShowAlert
        message={message}
        setShowAlertModal={setShowAlertModal}
        showAlertModal={showAlertModal}
      />
    </>
  );
}

export default Doctor;
