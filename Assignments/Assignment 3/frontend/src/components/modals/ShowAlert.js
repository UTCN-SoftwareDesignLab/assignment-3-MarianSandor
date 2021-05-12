import React, { useEffect } from "react";
import ReactDom from "react-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import style from "../components.module.css";

export default function Modal({ message, showAlertModal, setShowAlertModal }) {
  if (!showAlertModal) return null;

  return ReactDom.createPortal(
    <>
      <div className={style.overlay} />
      <div className={style.messageModal}>
        <h1>{message}</h1>
        <button
          className={style.closeButton}
          onClick={setShowAlertModal(false)}
        >
          X
        </button>
      </div>
    </>,
    document.getElementById("portal")
  );
}
