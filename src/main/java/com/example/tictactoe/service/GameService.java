package com.example.tictactoe.service;

// GameService: keeps hold of all the games in progress, can create a new one, can fetch one by ID
// deals with games. stringify in controller

import com.example.tictactoe.model.Game;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class GameService {
//    private static final Logger LOG = LoggerFactory.getLogger(GameService.class);

    private final Map<String, Game> games = new HashMap<>();

    public Game createNewGame() {
        String uuid=UUID.randomUUID().toString();
        Game newGame = new Game(uuid);
        games.put(uuid, newGame);
        return newGame;
    }

    public Optional<Game> getGame(String gameId) {
        return Optional.ofNullable(games.get(gameId));
    }
}
