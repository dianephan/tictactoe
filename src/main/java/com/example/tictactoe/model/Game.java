package com.example.tictactoe.model;

// Game: represents a single game, including id, board state, who's move is it next, etc?
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class Game {
    private static final Logger LOG = LoggerFactory.getLogger(Game.class);

    private final String id;
    private Cell cell1 = Cell.EMPTY;
    private Cell cell2 = Cell.EMPTY;
    private Cell cell3 = Cell.EMPTY;
    private Cell cell4 = Cell.EMPTY;
    private Cell cell5 = Cell.EMPTY;
    private Cell cell6 = Cell.EMPTY;
    private Cell cell7 = Cell.EMPTY;
    private Cell cell8 = Cell.EMPTY;
    private Cell cell9 = Cell.EMPTY;

    public Game(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String toString(){
        String board =
                cell1 + " | " + cell2 + " | " + cell3 + "\n" +
                        "----------\n" +
                cell4 + " | " + cell5 + " | " + cell6 + "\n" +
                        "----------\n" +
                cell7 + " | " + cell8 + " | " + cell9 + "\n";
        return board;
    }

    public void changeCell(String piece, Integer position) {
        Cell myCell = null;
        switch (position) {
            case 1:
                if (cell1.equals(Cell.EMPTY)) {
                    this.cell1 = Cell.valueOf(piece);
                    LOG.info("changing cell {} to {}", position, cell1);
                } else {
                    LOG.info("cell is unavailable");
                }
                break;
            case 2:
                if (cell2.equals(Cell.EMPTY)) {
                    cell2 = Cell.valueOf(piece);
                    LOG.info("changing cell {} to {}", position, cell2);
                } else {
                    LOG.info("cell is unavailable");
                }
                break;
            case 3:
                if (cell3.equals(Cell.EMPTY)) {
                    cell3 = Cell.valueOf(piece);
                    LOG.info("changing cell {} to {}", position, cell3);
                } else {
                    LOG.info("cell is unavailable");
                }
                break;
            case 4:
                if (cell4.equals(Cell.EMPTY)) {
                    cell4 = Cell.valueOf(piece);
                    LOG.info("changing cell {} to {}", position, cell4);
                } else {
                    LOG.info("cell is unavailable");
                }
                break;
            case 5:
                if (cell5.equals(Cell.EMPTY)) {
                    cell5 = Cell.valueOf(piece);
                    LOG.info("changing cell {} to {}", position, cell5);
                } else {
                    LOG.info("cell is unavailable");
                }
                break;
            case 6:
                if (cell6.equals(Cell.EMPTY)) {
                    cell6 = Cell.valueOf(piece);
                    LOG.info("changing cell {} to {}", position, cell6);
                } else {
                    LOG.info("cell is unavailable");
                }
                break;
            case 7:
                if (cell7.equals(Cell.EMPTY)) {
                    cell7 = Cell.valueOf(piece);
                    LOG.info("changing cell {} to {}", position, cell7);
                } else {
                    LOG.info("cell is unavailable");
                }
                break;
            case 8:
                if (cell8.equals(Cell.EMPTY)) {
                    cell8 = Cell.valueOf(piece);
                    LOG.info("changing cell {} to {}", position, cell8);
                } else {
                    LOG.info("cell is unavailable");
                }
                break;
            case 9:
                if (cell9.equals(Cell.EMPTY)) {
                    cell9 = Cell.valueOf(piece);
                    LOG.info("changing cell {} to {}", position, cell9);
                } else {
                    LOG.info("cell is unavailable");
                }
                break;

        }
    }

}