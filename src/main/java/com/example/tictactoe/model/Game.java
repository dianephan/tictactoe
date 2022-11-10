package com.example.tictactoe.model;

// Game: represents a single game, including id, board state, who's move is it next, etc?

public class Game {

    private final String id;
    private Cell cell1 = Cell.EMPTY;
    private Cell cell2 = Cell.O;
    private Cell cell3 = Cell.EMPTY;
    private Cell cell4 = Cell.X;
    private Cell cell5 = Cell.EMPTY;
    private Cell cell6 = Cell.EMPTY;
    private Cell cell7 = Cell.EMPTY;
    private Cell cell8 = Cell.O;
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
                        "---------\n" +
                cell4 + " | " + cell5 + " | " + cell6 + "\n" +
                        "---------\n" +
                cell7 + " | " + cell8 + " | " + cell9 + "\n";

        return board;

    }

}
