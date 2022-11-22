package com.example.tictactoe.controller;

import com.example.tictactoe.model.Cell;
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
        // also display whose turn it is or none at all
        return ResponseEntity.of(myGameService
                .getGame(gameId)
                .map(Game::toString));
    }

    @PostMapping("/game/{game-id}/{piece}/{position}")
    public ResponseEntity<String> movePiece(@PathVariable("game-id") String gameId,
                                            @PathVariable("piece") String piece,
                                            @PathVariable("position") Integer position) {
        Optional<Game> currentGame = myGameService.getGame(gameId);

        if (!currentGame.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No such game");
        }
//        if (currentGame.isPresent() && (!Objects.equals(piece, "X") || !piece.equals("O"))){
        if (!isValidPiece(piece)) {
            LOG.info("current game exists but input is not X or O");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Must be X or O");
        }
        if (isValidPosition(position)) {
            LOG.info("current game exists but position out of bound");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Spot is out of bounds");
        }

        Game theGame = currentGame.get();
        boolean wasTheMoveAllowed = theGame.changeCell(Cell.valueOf(piece), position);
        if (wasTheMoveAllowed) {
            return ResponseEntity.ok(theGame.toString());
        // need to check if your turn

        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Spot is taken.");
        }
    }

    private boolean isValidPosition(@PathVariable("position") Integer position) {
        return position < 1 || position > 9;
    }

    private boolean isValidPiece(@PathVariable("piece") String piece) {
        return piece.equals("X") || piece.equals("O");
    }
}
