// import React, { useEffect, useState } from 'react';
import './Board.css';
// if box == X or O cannot make move
// if currentState = X then "mark box with X"? 

function Board({ game, makeMove }) {

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
        makeMove(n);
      } // otherwise you can't play here so just ignore.
    }
  }

  return (
    <div className="game-board">
      <div>
        <div className="box" onClick={clicked(0)}>{cellDisplay(0)}</div>
        <div className="box" onClick={clicked(1)}>{cellDisplay(1)}</div>
        <div className="box" onClick={clicked(2)}>{cellDisplay(2)}</div>
      </div>
      <div>
        <div className="box" onClick={clicked(3)}>{cellDisplay(3)}</div>
        <div className="box" onClick={clicked(4)}>{cellDisplay(4)}</div>
        <div className="box" onClick={clicked(5)}>{cellDisplay(5)}</div>
      </div>
      <div>
        <div className="box" onClick={clicked(6)}>{cellDisplay(6)}</div>
        <div className="box" onClick={clicked(7)}>{cellDisplay(7)}</div>
        <div className="box" onClick={clicked(8)}>{cellDisplay(8)}</div>
      </div>
    </div>
  )
}

export default Board;  