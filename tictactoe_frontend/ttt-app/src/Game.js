import { useEffect, useState } from 'react'
import Board from "./Board";

function Game({ gameId }) {

  const [game, setGame] = useState(null);
  const [question, setQuestion] = useState(["Quiz question appears here"])

  const fetchGame = (gameId) => {
    fetch(`/game/${gameId}`, { method: "GET" })
      .then(response => response.json())
      .then(responseJson => {
        setGame(responseJson.game);
        console.log("Fetched game from server");
        console.log({ responseJson });
      });
  }

  const displayQuestion = async (n) => {
    var piece;
    if (game.currentState === "X_TURN") {
      piece = "X";
    } else if (game.currentState === "O_TURN") {
      piece = "O";
    }
    const returnResponse = await fetch(`/game/${gameId}/${piece}/${n+1}`, { method: "GET" })
    const obj = await returnResponse.text()
    setQuestion(obj);
    return obj;
  }

  const questionCorrect = async (position, option) => {
    var piece;
    if (game.currentState === "X_TURN") {
      piece = "X";
    } else if (game.currentState === "O_TURN") {
      piece = "O";
    }
    const returnResponse = await fetch(`/game/${gameId}/${piece}/${position+1}/${option}`, { method: "GET" })
    const obj = await returnResponse.text()
    return obj; 
  }

  
  const makeMove = (n) => {
    var piece;
    if (game.currentState === "X_TURN") {
      piece = "X";
    } else if (game.currentState === "O_TURN") {
      piece = "O";
    }

    // make an API call to the server to make the move of piece at cell n
    // n +1 because Game.java lol 
    fetch(`/game/${gameId}/${piece}/${n+1}`, { method: "POST" })
      .then(response => response.json())
      .then(responseJson => {
        setGame(responseJson.game);
        console.log({ responseJson });
      });
  }

  useEffect(() => {
    fetchGame(gameId);
  },
    [gameId,]);

  if (game === null) {
    return <div>Loading...</div>
  }
  
  return (
  <div style={{ fontSize: "20px" }}>
    <title>{gameId} in session </title>
    <h4>{gameId} in session </h4>
    <h4>{game.currentState}</h4>
    <p>
    <Board game={game} 
      makeMove={makeMove} 
      displayQuestion={displayQuestion} 
      question = {question}
      questionCorrect = {questionCorrect} 
      />
    </p>
  </div>);
}

export default Game;