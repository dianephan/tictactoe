import './App.css';
import React, { useEffect, useState } from 'react';
import {
  BrowserRouter as Router,
  Switch,
  Route,
  useParams,
} from "react-router-dom";

function Home() {
  const [gameInfo, setGameInfo] = useState({ gameid: "" });
  const [localUser, setLocalUser] = useState(null);

  const handleSubmit = (event) => {
    event.preventDefault();
    setGameInfo({ ...gameInfo, [event.target.gameid]: createGame() });
    console.log("user entered the following : ", gameInfo);
  };

  const createGame = () => {
    console.log("inside the creategame func"); 
    fetch(`/game`, { method: "POST" })
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

  console.log("gameinfo entered = ", gameInfo);

  return (
    <div className="App">
      <header className="App-header">
        <p>
          <code>enter your game id to get started</code>
        </p>

        <form action="/game" method="GET" enctype="multipart/form-data">
          <label>
            <code>game id</code>
            <br></br>
            <input
              type="text"
              placeholder="enter game id"
              value={gameInfo}
              onChange={(event) => setGameInfo(event.target.value)}
            ></input>
          </label>
          <input type="submit" value="find game" />
        </form>
        <br></br>

        <form action="/game" method="POST" enctype="multipart/form-data">
          <label>
            <code>create new game</code>
            <br></br>
          </label>
          <input
            type="submit"
            name="creategame"
            value={gameInfo.gameid}
            onChange={(event) => createGame()}
          />
        </form>

      </header>
    </div>
  );
}

export default Home;