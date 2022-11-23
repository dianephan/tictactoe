package com.example.tictactoe.model;

public enum GameState {
    X_TURN,
    O_TURN,
    DRAW,
    X_VICTORY,
    O_VICTORY,
    SPOT_TAKEN;

    public GameState switchTurns() {
        switch(this) {
            case X_TURN: return GameState.O_TURN;
            case O_TURN: return GameState.X_TURN;
            default: throw new IllegalStateException("No one else's turn?");
        }
    }
}
