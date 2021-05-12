import React, { useState, createContext } from "react";

export const MessageContext = createContext();

export const MessageInfo = (props) => {
  const [message, setMessage] = useState("");

  return (
    <MessageContext.Provider value={[message, setMessage]}>
      {props.children}
    </MessageContext.Provider>
  );
};

export const MessageModalContext = createContext();

export const MessageModalInfo = (props) => {
  const [showAlertModal, setShowAlertModal] = useState(false);

  return (
    <MessageModalContext.Provider value={[showAlertModal, setShowAlertModal]}>
      {props.children}
    </MessageModalContext.Provider>
  );
};
