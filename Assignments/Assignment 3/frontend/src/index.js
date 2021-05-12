import React from "react";
import ReactDOM from "react-dom";
import "./index.css";
import App from "./App";
import reportWebVitals from "./reportWebVitals";
import { AuthenticationInfo } from "./contexts/Authentication";
import { MessageInfo, MessageModalInfo } from "./contexts/Message";

ReactDOM.render(
  <React.StrictMode>
    <AuthenticationInfo>
      <MessageInfo>
        <MessageModalInfo>
          <App />
        </MessageModalInfo>
      </MessageInfo>
    </AuthenticationInfo>
  </React.StrictMode>,
  document.getElementById("root")
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
