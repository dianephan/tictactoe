import './Board.css';
import { useEffect, useState } from 'react'

function Board({
  game,
  makeMove,
  displayQuestion,
  question,
  questionCorrect,
}) {

  const [position, setPosition] = useState([0])
  const [answer, setAnswer] = useState(0)

  const displayValues = {
    "X": "✕",
    "O": "◯",
    "EMPTY": ""
  }

  const cellDisplay = (n) => {
    return displayValues[game.cells[n]];
  }

  const clicked = (n) => {
    return (e) => {
      if (game.cells[n] === "EMPTY") {
        setAnswer(0)
        displayQuestion(n);
        setPosition(n)
      }
    }
  }

  const answerClick = (option) => {
    return async (e) => {
      console.log("you clicked ", option, " for position ", position + 1)
      const result = await questionCorrect(position, option)  // this gives us the promise
      setAnswer(result)
      if (result === "true") {
        makeMove(position)
        console.log("correct")
      } else {
        console.log("cannot make move")
      }
    }
  }

  return (
    <div className="game-board">
      <div>
        <div className="box" onClick={clicked(0)}>{cellDisplay(0)}</div>
        <div className="box" onClick={clicked(3)}>{cellDisplay(3)}</div>
        <div className="box" onClick={clicked(6)}>{cellDisplay(6)}</div>
      </div>
      <div>
        <div className="box" onClick={clicked(1)}>{cellDisplay(1)}</div>
        <div className="box" onClick={clicked(4)}>{cellDisplay(4)}</div>
        <div className="box" onClick={clicked(7)}>{cellDisplay(7)}</div>
      </div>
      <div>
        <div className="box" onClick={clicked(2)}>{cellDisplay(2)}</div>
        <div className="box" onClick={clicked(5)}>{cellDisplay(5)}</div>
        <div className="box" onClick={clicked(8)}>{cellDisplay(8)}</div>
      </div>
      <p>
        <div className='quiz-question'> {question}
          <br></br>
          {answer}
          <div className='answer-board'>
            <div className='child' onClick={answerClick(1)}>1</div>
            <div className='child' onClick={answerClick(2)}>2</div>
            <div className='child' onClick={answerClick(3)}>3</div>
            <div className='child' onClick={answerClick(4)}>4</div>
          </div>
        </div>
      </p >
    </div >
  )
}

export default Board;  