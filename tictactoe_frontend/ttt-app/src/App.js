import logo from './logo.svg';
import './App.css';
import Home from './Home.js';
import Game from './Game.js';
import {
  BrowserRouter as Router,
  Switch,
  Route,
} from "react-router-dom";

function App() {

  return (
    <Router>
      <Switch>
        <Route path = "/game/:id">
          <Game />
        </Route>
        <Route path = "/">
          <Home></Home>
        </Route>
      </Switch>
    </Router>

  );
}

export default App;
