package com.example.tictactoe;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GameTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate client;

    private static class NewGameResponse{
        public String gameId;
        // {"gameId":"67c6db28-729e-4e5c-b2a9-2ce283095c3c"}
    }

    private static class Game {
        public String id;
        public List<String> cells;
        public String currentState;
        // {"id":"b81feb4e-fbd5-4d5d-a896-ec04801946bb",
        //        // "cells":["EMPTY","EMPTY","EMPTY","EMPTY","EMPTY","EMPTY","EMPTY","EMPTY","EMPTY"],"
        //        currentState":"X_TURN"}
    }

    private static class GameResponseWrapper{
            public Game game;
        // {"game":{"id":"b81feb4e-fbd5-4d5d-a896-ec04801946bb",
        // "cells":["EMPTY","EMPTY","EMPTY","EMPTY","EMPTY","EMPTY","EMPTY","EMPTY","EMPTY"],
        // currentState":"X_TURN"}}
    }

    private static class ErrorBody{
        public String message;
        public int status;
    }

    private String createNewGame(){
        return client.postForObject("http://localhost:" + port + "/game", "",
                NewGameResponse.class).gameId;
    }

    private Game getGameById(String gameId){
        return client.getForObject("http://localhost:" + port + "/game/" + gameId,
                GameResponseWrapper.class).game;
    }

    private Game makeNewMove(String gameId, String piece, Integer position){
         return client.postForObject("http://localhost:" + port +
                 "/game/" + gameId +"/"+ piece +"/"+ position, "",
                 GameResponseWrapper.class).game;
    }

    private ErrorBody makeNewMoveErrors(String gameId, String piece, Integer position){
        return client.postForObject("http://localhost:" + port +
                "/game/" + gameId +"/"+ piece +"/"+ position,
                "", ErrorBody.class);
    }

    @Test
    public void getNewGame() {
        String gameId = createNewGame();
        Game newGame = getGameById(gameId);
        assertThat(newGame.currentState).isEqualTo("X_TURN");
    }

    @Test
    public void makeFirstMove() {
        String gameId = createNewGame();
        Game afterFirstMove = makeNewMove(gameId, "X", 1);
        assertThat(afterFirstMove.currentState).isEqualTo("O_TURN");
        assertThat(afterFirstMove.cells.get(0)).isEqualTo("X");
    }

    @Test
    public void pieceXWins() {
        String gameId = createNewGame();
        makeNewMove(gameId, "X", 6);
        makeNewMove(gameId, "O", 1);
        makeNewMove(gameId, "X", 5);
        makeNewMove(gameId, "O", 2);
        Game result = makeNewMove(gameId, "X", 4);
        assertThat(result.currentState).isEqualTo("X_VICTORY");
    }

    @Test
    public void pieceOWins() {
        String gameId = createNewGame();
        makeNewMove(gameId, "X", 6);
        makeNewMove(gameId, "O", 1);
        makeNewMove(gameId, "X", 5);
        makeNewMove(gameId, "O", 2);
        makeNewMove(gameId, "X", 9);
        Game result = makeNewMove(gameId, "O", 3);
        assertThat(result.currentState).isEqualTo("O_VICTORY");
    }

    @Test
    public void gameDraw() {
        String gameId = createNewGame();
        makeNewMove(gameId, "X", 1);
        makeNewMove(gameId, "O", 2);
        makeNewMove(gameId, "X", 5);
        makeNewMove(gameId, "O", 3);
        makeNewMove(gameId, "X", 6);
        makeNewMove(gameId, "O", 4);
        makeNewMove(gameId, "X", 7);
        makeNewMove(gameId, "O", 9);
        Game result = makeNewMove(gameId, "X", 8);
        assertThat(result.currentState).isEqualTo("DRAW");
        assertThat(result.cells).doesNotContainAnyElementsOf(Collections.singleton("EMPTY"));
        assertThat(result.cells).doesNotContain("EMPTY");
    }

    @Test
    public void gameIdDoesNotExist() {
        String gameId = "this-game-does-not-exist";
        ErrorBody result = makeNewMoveErrors(gameId, "X", 6);
        assertThat(result.status).isEqualTo(404);
        assertThat(result.message).contains("Game is not found.");
    }

    @Test
    public void makeMoveOverAnotherPiece() {
        String gameId = createNewGame();
        makeNewMove(gameId, "X", 1);
        ErrorBody result = makeNewMoveErrors(gameId, "O", 1);
        assertThat(result.status).isEqualTo(400);
        assertThat(result.message).contains("Spot was taken or it's not your turn.");
    }

    @Test
    public void invalidPiece() {
        String gameId = createNewGame();
        Random r = new Random();
        String randomAlpha = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNPQRSTUVWYZ";
        for (char letter : randomAlpha.toCharArray()) {
            ErrorBody result = makeNewMoveErrors(gameId, letter+"", 1);
            assertThat(result.status).isEqualTo(400);
            assertThat(result.message).contains("Input must be X or O.");
        }
    }

    @Test
    public void positionOutOfBounds () {
        String gameId = createNewGame();
        ErrorBody result = makeNewMoveErrors(gameId, "X", 10);
        assertThat(result.status).isEqualTo(400);
        assertThat(result.message).contains("Position is out of bounds.");
        result = makeNewMoveErrors(gameId, "X", -1);
        assertThat(result.status).isEqualTo(400);
        assertThat(result.message).contains("Position is out of bounds.");
    }

    @Test
    public void wrongTurn () {
        String gameId = createNewGame();
        ErrorBody result = makeNewMoveErrors(gameId, "O", 1);
        assertThat(result.status).isEqualTo(400);
        assertThat(result.message).contains("Spot was taken or it's not your turn.");
    }

    @Test
    public void invalidMoveGameOver() {
        String gameId = createNewGame();
        makeNewMove(gameId, "X", 6);
        makeNewMove(gameId, "O", 1);
        makeNewMove(gameId, "X", 5);
        makeNewMove(gameId, "O", 2);
        makeNewMove(gameId, "X", 4);
        ErrorBody result = makeNewMoveErrors(gameId, "O", 3);
        assertThat(result.status).isEqualTo(400);
        assertThat(result.message).contains("Game is already over.");
    }
}
