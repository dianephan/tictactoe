import { useEffect, useState } from 'react'
import Board from "./Board";

function Game({ gameId }) {

  const [game, setGame] = useState(null);
  const [question, setQuestion] = useState(["Quiz question appears here"])
  const [answerChoices, setAnswerChoices] = useState(['answer choices here'])

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
    var piece = checkPiece(game.currentState);
    const returnResponse = await fetch(`/game/${gameId}/${piece}/${n+1}`, { method: "GET" })
    const obj = await returnResponse.json()
    const answers = [obj.ans_one, obj.ans_two, obj.ans_three, obj.ans_four, obj.correct_ans]
    setQuestion(obj.question);
    setAnswerChoices(answers)
  }


  const makeMove = (n) => {
    var piece = checkPiece(game.currentState)
    // make an API call to the server to make the move of piece at cell n
    // n +1 because Game.java lol 
    fetch(`/game/${gameId}/${piece}/${n+1}`, { method: "POST" })
      .then(response => response.json())
      .then(responseJson => {
        setGame(responseJson.game);
        console.log({ responseJson });
      });
  }

  const checkPiece = (currentState) => {
    var piece; 
    if (currentState === "X_TURN") {
      piece = "X";
    } else if (currentState === "O_TURN") {
      piece = "O";
    }
    return piece;
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
      answers = {answerChoices}
      />
    </p>
  </div>);
}

export default Game;