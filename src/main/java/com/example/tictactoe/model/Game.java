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

    private Integer capacity = 0;

    public Game(String id) {
        this.id = id;
        Arrays.fill(cells, Cell.EMPTY);
        currentState = GameState.X_TURN;
    }

    public String getId() {
        return id;
    }

    public String toString() {
        return
                currentState.toString() + "\n" +
                        cells[0] + " | " + cells[1] + " | " + cells[2] + "\n" +
                        "----------\n" +
                        cells[3] + " | " + cells[4] + " | " + cells[5] + "\n" +
                        "----------\n" +
                        cells[6] + " | " + cells[7] + " | " + cells[8] + "\n";
    }

    public GameState getCurrentState() {
        return currentState;
    }

    public boolean changeCell(Cell piece, Integer position) {
        // if the space isn't EMPTY return false
        if (!cells[position - 1].equals(Cell.EMPTY)) {
//            currentState = GameState.SPOT_TAKEN;
            return false;
        }
        if (currentState.equals(GameState.DRAW)
            || currentState.equals(GameState.O_VICTORY)
            || currentState.equals(GameState.X_VICTORY)) {
            return false;
        }
        if (pieceXturnO(piece) || pieceOturnX(piece)) {
            return false;
        } else {
            // you know that the piece is correct so time to
            cells[position - 1] = piece;
            capacity += 1;
            currentState = currentState.switchTurns();
            // if those surrounding the piece are same, then Victory
            if ((cells[0].equals(piece) && cells[1].equals(piece) && cells[2].equals(piece))
                    || (cells[3].equals(piece) && cells[4].equals(piece) && cells[5].equals(piece))
                    || (cells[6].equals(piece) && cells[7].equals(piece) && cells[8].equals(piece))
                    || (cells[0].equals(piece) && cells[3].equals(piece) && cells[6].equals(piece))
                    || (cells[1].equals(piece) && cells[4].equals(piece) && cells[7].equals(piece))
                    || (cells[2].equals(piece) && cells[5].equals(piece) && cells[8].equals(piece))
                    || (cells[0].equals(piece) && cells[4].equals(piece) && cells[8].equals(piece))
                    || (cells[2].equals(piece) && cells[4].equals(piece) && cells[6].equals(piece))
            ) {
                if (piece.equals(Cell.X)) {
                    currentState = GameState.X_VICTORY;
                } else {
                    currentState = GameState.O_VICTORY;
                }
                if (capacity.equals(9)) {
                    // this is not hit yet
                    currentState = GameState.DRAW;
                }
            }
        }
        return true;
    }

    private boolean pieceXturnO(Cell piece) {
        return piece.equals(Cell.X) && currentState.equals(GameState.O_TURN);
    }
    private boolean pieceOturnX(Cell piece) {
        return piece.equals(Cell.O) && currentState.equals(GameState.X_TURN);
    }

}
