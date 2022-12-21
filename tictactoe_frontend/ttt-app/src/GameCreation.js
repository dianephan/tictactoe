import './App.css';
import React, { useState } from 'react';

function GameCreation({setGameId}) {
  const [gameInfo, setGameInfo] = useState({ gameId: "" });

  const createGame = () => {
    fetch(`/game`, { method: "POST" })
      .then(response => response.json())
      .then(responseJson => {
        setGameInfo(responseJson);
      });
  }

  return (
    <div className="App">
      <header className="App-header">
        <p>
          <code>enter your game id to get started</code>
        </p>

        <code>game id</code>
        <br></br>
        <input
          type="text"
          value={gameInfo.gameId}
          onChange={(e => setGameInfo({gameId: e.target.value}))}
        ></input>
        <input
          type="button"
          value="join existing game"
          onClick={(e => setGameId(gameInfo.gameId))}
        ></input>

        <br></br>
          <input
            type="button"
            value= "create new game"
            onClick={(event) => createGame()}
          />
      </header>
    </div>
  );
}

export default GameCreation;