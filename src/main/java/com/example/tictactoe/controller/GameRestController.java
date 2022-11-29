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

    @PostMapping("/game/{game-id}/{piece}/{position}/{answer}")
    public ResponseEntity<String> movePiece(@PathVariable("game-id") String gameId,
                                            @PathVariable("piece") String piece,
                                            @PathVariable("position") Integer position,
                                            @PathVariable("answer") Integer answer) {
        Optional<Game> currentGame = myGameService.getGame(gameId);

        if (!currentGame.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No such game");
        }
        if (!isValidAnswer(answer)){
            LOG.info("not a valid answer input");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Must be either 1 2 3 or 4");
        }
        if (!isValidPiece(piece)) {
            LOG.info("current game exists but input is not X or O");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Must be X or O");
        }
        if (isValidPosition(position)) {
            LOG.info("current game exists but position out of bound");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Spot is out of bounds");
        }
        Game theGame = currentGame.get();
        boolean wasTheMoveAllowed = theGame.changeCell(Cell.valueOf(piece), position, answer);
        if (wasTheMoveAllowed) {

            return ResponseEntity.ok(theGame.toString());
        }
        else {
            GameState gameState = theGame.getCurrentState();
            if (isGameOver(gameState)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gameState.toString());
            }
            if (isGameInSession(gameState)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gameState.toString());
            }
            // this does not get run
            else { return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Spot is taken."); }
        }
    }

    private boolean isValidAnswer(Integer answer) {
        return answer >= 1 && answer <= 4;
    }

    private boolean isGameInSession(GameState gameState) {
        return gameState.equals(GameState.X_TURN) || gameState.equals(GameState.O_TURN);
    }

    private boolean isGameOver(GameState gameState) {
        return gameState.equals(GameState.DRAW) || gameState.equals(GameState.O_VICTORY) || gameState.equals(GameState.X_VICTORY);
    }

    private boolean isValidPosition(@PathVariable("position") Integer position) {
        return position < 1 || position > 9;
    }

    private boolean isValidPiece(@PathVariable("piece") String piece) {
        return piece.equals("X") || piece.equals("O");
    }
}
