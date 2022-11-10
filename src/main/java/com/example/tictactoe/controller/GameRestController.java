package com.example.tictactoe.controller;

import com.example.tictactoe.model.Game;
import com.example.tictactoe.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class GameRestController {

    private final GameService myGameService;

    @Autowired
    public GameRestController(GameService gameService) {
        myGameService = gameService;
    }

    @PostMapping("/game")
    public String createNewGame() {
        Game game = myGameService.createNewGame();
        return game.getId();
    }

    @GetMapping("/game/{game-id}")
    public ResponseEntity<String> getGame(@PathVariable("game-id") String gameId) {
        return ResponseEntity.of(myGameService
                .getGame(gameId)
                .map(Game::toString));
    }
}
