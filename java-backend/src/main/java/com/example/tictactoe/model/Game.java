package com.example.tictactoe.model;

// Game: represents a single game, including id, board state, who's move is it next, etc?
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static com.example.tictactoe.model.GameState.*;
import static com.example.tictactoe.model.Cell.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Game {
    private static final Logger LOG = LoggerFactory.getLogger(Game.class);

    private final String id;

    private final Cell[] cells = new Cell[9];

    private GameState currentState;

    private final Map<Integer, Integer> questions = new HashMap<>();
    private int countOfPiecesPlaced = 0;

    public Game(String id) {
        this.id = id;
        Arrays.fill(cells, Cell.EMPTY);
        questions.put(1, 3);
        questions.put(2, 1);
        questions.put(3, 3);
        questions.put(4, 2);
        questions.put(5, 1);
        questions.put(6, 3);
        questions.put(7, 3);
        questions.put(8, 4);
        questions.put(9, 2);
        currentState = X_TURN;
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

    public Cell[] getCells() {
        return cells;
    }

    public boolean changeCell(Cell piece, Integer position) {
        int ourPosition = position - 1;
        if (!cells[ourPosition].equals(EMPTY)) {
            // ideally should return this message in Controller
            LOG.info("Spot is taken.");
            return false;
        }
        if (currentState.equals(DRAW)
            || currentState.equals(O_VICTORY)
            || currentState.equals(X_VICTORY)) {
            return false;
        }
        if (pieceXturnO(piece) || pieceOturnX(piece)) {
            return false;
        } else {
            cells[ourPosition] = piece;
            countOfPiecesPlaced += 1;

            // answerIsCorrect return true
            // else false

            currentState = currentState.switchTurns();
            // determine victory
            if ((cells[0].equals(piece) && cells[1].equals(piece) && cells[2].equals(piece))
                    || (cells[3].equals(piece) && cells[4].equals(piece) && cells[5].equals(piece))
                    || (cells[6].equals(piece) && cells[7].equals(piece) && cells[8].equals(piece))
                    || (cells[0].equals(piece) && cells[3].equals(piece) && cells[6].equals(piece))
                    || (cells[1].equals(piece) && cells[4].equals(piece) && cells[7].equals(piece))
                    || (cells[2].equals(piece) && cells[5].equals(piece) && cells[8].equals(piece))
                    || (cells[0].equals(piece) && cells[4].equals(piece) && cells[8].equals(piece))
                    || (cells[2].equals(piece) && cells[4].equals(piece) && cells[6].equals(piece))
            ) {
                if (piece.equals(X)) {
                    currentState = X_VICTORY;
                }
                if (piece.equals(O)) {
                    currentState = O_VICTORY;
                }
            }
            else if (isNoVictory() && countOfPiecesPlaced == 9) {
                LOG.info(String.valueOf(countOfPiecesPlaced));
                currentState = DRAW;
            }
        }
        return true;
    }

    private boolean isNoVictory() {
        return !(currentState.equals(O_VICTORY) || currentState.equals(X_VICTORY));
    }

    private boolean pieceXturnO(Cell piece) {
        return piece.equals(X) && currentState.equals(O_TURN);
    }
    private boolean pieceOturnX(Cell piece) {
        return piece.equals(O) && currentState.equals(X_TURN);
    }

    public boolean isOver() {
        return currentState.equals(DRAW) || currentState.equals(O_VICTORY) || currentState.equals(X_VICTORY);
    }

    public boolean isInSession() {
        return currentState.equals(X_TURN) || currentState.equals(O_TURN);
    }
}
