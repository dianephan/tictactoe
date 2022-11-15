package com.example.tictactoe.service;

// GameService: keeps hold of all the games in progress, can create a new one, can fetch one by ID
// deals with games. stringify in controller

import com.example.tictactoe.model.Cell;
import com.example.tictactoe.model.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class GameService {
    private static final Logger LOG = LoggerFactory.getLogger(GameService.class);

    // 34653470 => $game, 453782047 => $another_game
    private final Map<String, Game> games = new HashMap<String, Game>();

    public Game createNewGame() {
        String uuid=UUID.randomUUID().toString();
        Game newGame = new Game(uuid);
        games.put(uuid, newGame);
        return newGame;
    }

    public Optional<Game> getGame(String gameId) {
        return Optional.ofNullable(games.get(gameId));
    }

    // this function does not seem necessary bc can use getGame
    public String validateID(String gameId) {
        if (games.containsKey(gameId)){
            return gameId;
        }
        return "Invalid ID";
    }

    // does this need to be optional if we already know this game exists
    public Optional<Game> movePiece(String gameId, String piece, Integer position){
        Game currentGame = games.get(gameId);
        LOG.info("about to changeCell for piece = {} and position = {}", piece, position);
        currentGame.changeCell(piece, position);
        return Optional.ofNullable(currentGame);
    }
}
