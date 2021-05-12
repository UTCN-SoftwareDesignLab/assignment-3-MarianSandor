import React, { useState, createContext } from "react";

export const AuthenticationContext = createContext();

export const AuthenticationInfo = (props) => {
  const [user, setUser] = useState({});

  return (
    <AuthenticationContext.Provider value={[user, setUser]}>
      {props.children}
    </AuthenticationContext.Provider>
  );
};
