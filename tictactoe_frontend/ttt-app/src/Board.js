import './Board.css';

function Board({ game, makeMove, displayQuestion, question }) {

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
        const textobj = displayQuestion(n);
      }
    }
  }

  const answerClick = (num) => {
    return (e) => {
      console.log("you clicked ", num)
      // check if answer correct before making move
      // if (questionCorrect) {
      //   makeMove(num, textobj);
      // } else {
      //   console.log("Wrong")
      // }
    }
  }

  // dispay question
  // if question is correct 
  // make post request to change gamestate 

  return (
    <div className="game-board">
      <div>
        <div className="box" onClick={
          clicked(0)
        }>{cellDisplay(0)}</div>

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
          <div className='answer-board'>
            <div className='child' onClick={answerClick(1)}>1</div>
            <div className='child' onClick={answerClick(2)}>2</div>
            <div className='child' onClick={answerClick(3)}>3</div>
            <div className='child' onClick={answerClick(4)}>4</div>
          </div>
        </div>
      </p>
    </div>
  )
}

export default Board;  