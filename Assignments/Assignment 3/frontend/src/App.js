import "./App.css";
import React, { useContext } from "react";
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Redirect,
} from "react-router-dom";
import Authentication from "./pages/Authentication";
import Secretary from "./pages/Secretary";
import Administrator from "./pages/Administrator";
import Doctor from "./pages/Doctor";
import { AuthenticationContext } from "./contexts/Authentication";

function App() {
  const [user, setUser] = useContext(AuthenticationContext);

  return (
    <Router>
      <div className="App">
        <Switch>
          <Route path="/" exact>
            <Authentication />
          </Route>
          <Route path="/secretary" exact>
            {Object.keys(user).length === 0 ? (
              <Redirect to="/" />
            ) : (
              <Secretary />
            )}
          </Route>
          <Route path="/administrator" exact>
            {Object.keys(user).length === 0 ? (
              <Redirect to="/" />
            ) : (
              <Administrator />
            )}
          </Route>
          <Route path="/doctor" exact>
            {Object.keys(user).length === 0 ? <Redirect to="/" /> : <Doctor />}
          </Route>
        </Switch>
      </div>
    </Router>
  );
}

export default App;
