package com.example.tictactoe.controller;

import com.example.tictactoe.model.Cell;
import com.example.tictactoe.model.Game;
import com.example.tictactoe.model.Question;
import com.example.tictactoe.service.GameService;
import com.example.tictactoe.repository.QuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Optional;

@RestController
public class GameRestController {
    private static final Logger LOG = LoggerFactory.getLogger(GameRestController.class);

    private final GameService myGameService;
    private QuestionRepository questionRepository;

    @Autowired
    public GameRestController(GameService gameService, QuestionRepository questionRepository) {
        myGameService = gameService;
        this.questionRepository = questionRepository;
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

    @GetMapping("/game/{game-id}/{piece}/{position}")
    public ResponseEntity<Question> displayQuestion(@PathVariable("game-id") String gameId,
                                                  @PathVariable("piece") String piece,
                                                  @PathVariable("position") Integer position) {
        return ResponseEntity.of(questionRepository.getRandomQuestion());
//                .findById(Long.valueOf(position)));
    }

//    @GetMapping("/game/{game-id}/{piece}/{position}/{answer}")
//    public boolean lookUpAnswer(@PathVariable("game-id") String gameId,
//                                               @PathVariable("piece") String piece,
//                                               @PathVariable("position") Integer position,
//                                               @PathVariable("answer") Integer answer) {
//
//        Optional<Game> currentGame = myGameService.getGame(gameId);
//        Game theGame = currentGame.get();
//
//        System.out.print(theGame.getQuestions());
//        boolean wasAnswerCorrect = theGame.checkAnswer(position, answer);
//        if (wasAnswerCorrect) {
//            return true;
//        }
//        return false;
//    }

    @PostMapping("/game/{game-id}/{piece}/{position}")
    public ResponseEntity<Map<String, Object>> movePiece(@PathVariable("game-id") String gameId,
                                                         @PathVariable("piece") String piece,
                                                         @PathVariable("position") Integer position) {

        Optional<Game> currentGame = myGameService.getGame(gameId);

        // Validate inputs
        if (currentGame.isEmpty()) {
            LOG.info("Request for non-existent game");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game is not found.");
        }

        if (!isValidPiece(piece)) {
            LOG.info("current game exists but input is not X or O");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Input must be X or O.");
        }
        if (isValidPosition(position)) {
            LOG.info("current game exists but position out of bound");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Position is out of bounds.");
        }

        Game theGame = currentGame.get();

        if (theGame.isOver()) {
            LOG.info("move made on finished game (VICTORY // DRAW)");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Game is already over.");
        }

        boolean wasTheMoveAllowed = theGame.changeCell(Cell.valueOf(piece), position);
        if (wasTheMoveAllowed) {
            // the move was allowed, return quic question

            return ResponseEntity.ok(Map.of("game", theGame));
        }
        // either spot is taken or same piece tried to go again
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Spot was taken or it's not your turn.");
    }

    private boolean isValidPosition(@PathVariable("position") Integer position) {
        return position < 1 || position > 9;
    }

    private boolean isValidPiece(@PathVariable("piece") String piece) {
        return piece.equals("X") || piece.equals("O");
    }
}
