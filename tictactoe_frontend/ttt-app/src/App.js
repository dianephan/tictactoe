import logo from './logo.svg';
import './App.css';
import React, { useEffect, useState } from 'react';
import {
  BrowserRouter as Router,
  Switch,
  Route,
  useParams,
} from "react-router-dom";
  
function IndividualGame() { 
  let { id } = useParams();
  return <div style = {{ fontSize: "50px" }}>
    Now showing post { id } 
  </div>;
}

function Home () {
  const [gameInfo, setGameInfo] = useState({
    gameid: "",
    exists: "",
  });

  const handleSubmit = (event) => {
    event.preventDefault();
    setGameInfo({ ...gameInfo, [event.target.gameid]: createGame()}); 
    console.log(gameInfo); 
  };

  // const fetchDetails = () => {
  //   fetch(`/game/${encodeURIComponent(gameInfo.gameid)}}`, {
  //     method: "GET"
  //     })
  //       // .then(response => response.json())
  //       // .then(response => console.log(response));  
  // }

  const createGame = () => {
    fetch(`/game`, {method:"POST"})
        .then(response => response.json())
        .then(response => setGameInfo(response.json()))
        .then(response => console.log("success for ", gameInfo.gameid))  
        // store in useState
  }

  useEffect(() => {
    // GET request using fetch inside useEffect React hook
    // created a new game here. need to store gameID in useState
    createGame(); 
    // console.log("game has been created", response.json.gameid)
}, []);

  console.log("gameinfo = ", gameInfo); 

  return (
    <div className="App">
      <header className="App-header">
        <p>
          <code>enter your game id to get started</code>
        </p>
        <form action = "/game" method = "GET" enctype = "multipart/form-data">
          <label>
            <code>game id</code>
            <br></br>
            <input 
              type = "text" 
              name = "lookup" 
              placeholder='enter game id'
              value = {gameInfo.exists}
            />
          </label>
            <input type="submit" value="find game"/>
        </form>
        <br></br>

        <form action = "/game" method = "POST" enctype = "multipart/form-data">
          <label>
            <code>create new game</code>
            <br></br>
          </label>
            <input 
              type="submit" 
              name = "creategame"
              value={gameInfo.gameid}
              onChange = {handleSubmit}
            />
        </form>

      </header>
    </div>
  );
}

function App() {

  return (
    <Router>
      <Switch>
        <Route path = "/game/:id">
          <IndividualGame />
        </Route>
        <Route path = "/">
          <Home></Home>
        </Route>
      </Switch>
    </Router>

  );
}

export default App;
