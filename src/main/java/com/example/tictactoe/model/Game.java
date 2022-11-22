package com.example.tictactoe.model;

// Game: represents a single game, including id, board state, who's move is it next, etc?
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class Game {
    private static final Logger LOG = LoggerFactory.getLogger(Game.class);

    private final String id;

    private final Cell[] cells = new Cell[9];

    private GameState currentState;

    public Game(String id) {
        this.id = id;
        Arrays.fill(cells, Cell.EMPTY);
        currentState = GameState.X_TURN;
    }

    public String getId() {
        return id;
    }

    public String toString(){
        return
                currentState.toString() + "\n" +
                cells[0] + " | " + cells[1] + " | " + cells[2] + "\n" +
                        "----------\n" +
                cells[3] + " | " + cells[4] + " | " + cells[5] + "\n" +
                        "----------\n" +
                cells[6] + " | " + cells[7] + " | " + cells[8] + "\n";
    }

    // this method returns false if the move was disallowed,
    // true if the move was placed and the board updated
    public boolean changeCell(Cell piece, Integer position) {
        // if the space isn't EMPTY return false
        if (!cells[position].equals(Cell.EMPTY)){
            return false;
        }
        // if the game is finished, do nothing (and return false)
        // DRAW, X_VIC, O_VIC
        if (currentState.equals(GameState.DRAW)
            || currentState.equals(GameState.O_VICTORY)
            || currentState.equals(GameState.X_VICTORY)) {
          return false;
        }
        // if the piece is X but it's O_TURN (and vice versa)
        // that's an error - return false
        if (pieceXturnO(piece)) {
            return false;
        }
        if (pieceOturnX(piece)){
            return false;
        }
        else {
            // make the move and change the state of the game

            // the move should be made later after checking whose turn
            // got out of bound errors
            cells[position - 1] = piece;
            // change turns
            if (piece.equals(Cell.X)) {
                currentState = GameState.O_TURN;
            }
            if (piece.equals(Cell.O)) {
                currentState = GameState.X_TURN;
            }
            if (position.equals(1)) {
                if ((cells[5].equals(piece) && cells[9].equals(piece))
                || (cells[4].equals(piece) && cells[7].equals(piece))
                || (cells[2].equals(piece) && cells[3].equals(piece))
                ) {
                    if (piece.equals(Cell.X)) {
                        currentState = GameState.X_VICTORY;
                    } else {
                        currentState = GameState.O_VICTORY;
                    }
                }
            }
            if (position.equals(2)) {
                if ((cells[5].equals(piece) && cells[8].equals(piece))
                    || (cells[1].equals(piece) && cells[3].equals(piece))
                ) {
                    if (piece.equals(Cell.X)) {
                        currentState = GameState.X_VICTORY;
                    } else {
                        currentState = GameState.O_VICTORY;
                    }
                }
            }
            if (position.equals(3)) {
                if ((cells[1].equals(piece) && cells[2].equals(piece))
                    || (cells[5].equals(piece) && cells[7].equals(piece))
                    || (cells[6].equals(piece) && cells[9].equals(piece))
                ) {
                    if (piece.equals(Cell.X)) {
                        currentState = GameState.X_VICTORY;
                    } else {
                        currentState = GameState.O_VICTORY;
                    }
                }
            }
//            if (cells.) {
//                currentState = GameState.DRAW;
//            }
        }
        // AND change currentState
        // if X was passed in, then change currentState to one of:
        //   X_VICTORY - this would be if there's 3 X's in a row somewhere
        //   DRAW      - this would be if the board is full
        //   O_TURN    - otherwise this
        return true;
    }
    private boolean pieceXturnO(Cell piece) {
        return piece.equals(Cell.X) && currentState.equals(GameState.O_TURN);
    }
    private boolean pieceOturnX(Cell piece) {
        return piece.equals(Cell.O) && currentState.equals(GameState.X_TURN);
    }
}
