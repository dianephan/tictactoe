package com.example.tictactoe.controller;

import com.example.tictactoe.model.Cell;
import com.example.tictactoe.model.Game;
import com.example.tictactoe.model.GameState;
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

import java.util.Map;
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
    public Map<String, String> createNewGame() {
        Game game = myGameService.createNewGame();
        return Map.of("gameId", game.getId());
    }

    @GetMapping("/game/{game-id}")
    public ResponseEntity<Map<String, Object>> getGame(@PathVariable("game-id") String gameId) {
        // also display whose turn it is or none at all
        return ResponseEntity.of(myGameService
                .getGame(gameId)
                .map(g -> Map.of("game", g)));
    }

    @PostMapping("/game/{game-id}/{piece}/{position}")
    public ResponseEntity<Map<String, Object>> movePiece(@PathVariable("game-id") String gameId,
                                                         @PathVariable("piece") String piece,
                                                         @PathVariable("position") Integer position) {
        Optional<Game> currentGame = myGameService.getGame(gameId);

        // TODO: Need to find way to pass error messages out

        // Validate inputs
        if (currentGame.isEmpty()) {
            LOG.info("Request for non-existent game");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        if (!isValidPiece(piece)) {
            LOG.info("current game exists but input is not X or O");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        if (isValidPosition(position)) {
            LOG.info("current game exists but position out of bound");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Game theGame = currentGame.get();

        if (theGame.isOver()) {
            LOG.info("move made on finished game (VICTORY // DRAW)");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        boolean wasTheMoveAllowed = theGame.changeCell(Cell.valueOf(piece), position);
        if (wasTheMoveAllowed) {
            // the move was allowed, return and we're done
            return ResponseEntity.ok(Map.of("game", theGame));
        }
        // either spot is taken or same piece tried to go again
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    private boolean isValidPosition(@PathVariable("position") Integer position) {
        return position < 1 || position > 9;
    }

    private boolean isValidPiece(@PathVariable("piece") String piece) {
        return piece.equals("X") || piece.equals("O");
    }
}
