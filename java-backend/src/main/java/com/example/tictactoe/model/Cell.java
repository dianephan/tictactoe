package com.example.tictactoe.model;

public enum Cell {
    X("X"),
    O("O"),
    EMPTY(" ");

    private final String display;

    Cell(String d){
        this.display = d;
    }

    @Override
    public String toString() {
        return display;
    }
}
