import React, { useState } from "react";
import LoginForm from "../components/authentication/LoginForm";
import SignUpForm from "../components/authentication/SignUpForm";

function Authentication() {
  const [login, setLogin] = useState(true);

  return (
    <div>
      {login ? (
        <LoginForm setLogin={setLogin}></LoginForm>
      ) : (
        <SignUpForm setLogin={setLogin}></SignUpForm>
      )}
    </div>
  );
}

export default Authentication;
