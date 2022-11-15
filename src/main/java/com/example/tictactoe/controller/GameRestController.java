package com.example.tictactoe.controller;

import com.example.tictactoe.model.Game;
import com.example.tictactoe.service.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class GameRestController {
    private static final Logger LOG = LoggerFactory.getLogger(GameRestController.class);

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

    @PostMapping("/game/{game-id}/{piece}/{position}")
    public ResponseEntity<String> movePiece(@PathVariable("game-id") String gameId,
                                            @PathVariable("piece") String piece,
                                            @PathVariable("position") Integer position) {
        Optional<Game> currentGame = myGameService.getGame(gameId);
//        if (currentGame.isPresent() && (!Objects.equals(piece, "X") || !piece.equals("O"))){
        if ((!piece.equals("X")) && (!piece.equals("O")) && currentGame.isPresent()){
            LOG.info("current game exists but input is not X or O");
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Must be X or O.");
        }
        if (currentGame.isPresent() && (position < 1 || position > 9)){
            LOG.info("current game exists but position out of bound");
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Spot is out of bounds");
        }
        return ResponseEntity.of(myGameService
                .movePiece(gameId, piece, position)
                .map(Game::toString));
    }
}
