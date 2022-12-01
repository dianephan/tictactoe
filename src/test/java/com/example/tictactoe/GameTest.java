package com.example.tictactoe;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;

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
    }

    // Success Scenarios:
    // 1. make 9 valid moves in a row & get a draw
    // 2. game is won in 5 moves by X
    // 3. game is won in 6 moves by O

    // Failure Scenarios:
    // 1. make a move over another piece -> error 400 w/ message
    //    assertEquals(200, responseEntity.getStatusCodeValue());
    // 2. Invalid piece (I, Y, A) some other letter
    // 2.5 Invalid move position needs to be 1-9 (invalid eg 10) (LATER ON: deal with "fkdsfjs")
    // 3. Invalid move (ie wrong turn)
    // 4. Invalid move (game is already finished)
    // 5. Invalid bc gameId does not exist


}
