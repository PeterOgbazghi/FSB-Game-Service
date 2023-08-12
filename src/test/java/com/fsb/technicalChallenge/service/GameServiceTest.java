package com.fsb.technicalChallenge.service;

import com.fsb.technicalChallenge.model.Game;
import com.fsb.technicalChallenge.pojo.CreateGamePayload;
import com.fsb.technicalChallenge.pojo.ResponsePayload;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameServiceTest {
    private GameServiceImpl gameService = new GameServiceImpl();

    private ResponsePayload responsePayloadsuccess;
    private ResponsePayload responsePayloadFailure;
    private ResponsePayload responsePayloadDeleteSuccess;
    private ResponsePayload responsePayloadDeleteFailure;

    @BeforeEach
    void setup() {
        responsePayloadsuccess = new ResponsePayload("SUCCESS","Game created!");
        responsePayloadFailure = new ResponsePayload("FAILED","Game already exists");
        responsePayloadDeleteSuccess = new ResponsePayload("SUCCESS","Game deleted successfully");
        responsePayloadDeleteFailure = new ResponsePayload("FAILED","Game doesn't exist!");
    }

    @ParameterizedTest
    @CsvSource(value = {"cod:2021/07/08:true", "cod2:2021/07/08:false", "cod3:2021/07/08:true"}, delimiter = ':')
    public void createGameSuccess(String name, String date, boolean active) throws Exception {
        CreateGamePayload gamePayload = new CreateGamePayload(name, date , active);
        Assertions.assertEquals(ResponseEntity.status(200).body(responsePayloadsuccess),gameService.createGame(gamePayload));
        gameService.deleteGame(name);
    }

    @Test
    public void createGamefail() throws Exception {
        CreateGamePayload gamePayload = new CreateGamePayload("COD", "2023/07/15" , true);
        gameService.createGame(gamePayload);
        Assertions.assertEquals(ResponseEntity.status(400).body(responsePayloadFailure),gameService.createGame(gamePayload));
        gameService.deleteGame("COD");
    }

    @ParameterizedTest
    @CsvSource(value = {"cod:2021/07/08:true", "cod2:2021/07/08:false", "cod3:2021/07/08:true"}, delimiter = ':')
    public void getGameSuccess(String name, String date, boolean active) throws Exception {
        CreateGamePayload gamePayload = new CreateGamePayload(name,date,active);
        Game game = new Game(name,date,active);
        gameService.createGame(gamePayload);
        Assertions.assertEquals(ResponseEntity.ok().body(game),gameService.getGame(name));
        gameService.deleteGame(name);
    }

    @ParameterizedTest
    @CsvSource(value = {"cod:2021/07/08:true", "cod2:2021/07/08:false", "cod3:2021/07/08:true"}, delimiter = ':')
    public void getGameFailure(String name, String date, boolean active) throws Exception {
        Assertions.assertEquals(ResponseEntity.status(204).body(null), gameService.getGame(name));
    }

    @ParameterizedTest
    @CsvSource(value = {"cod:2021/07/08:true", "cod2:2021/07/08:false", "cod3:2021/07/08:true"}, delimiter = ':')
    public void deleteGameSuccess(String name, String date, boolean active) throws Exception {
        CreateGamePayload gamePayload = new CreateGamePayload(name,date,active);
        gameService.createGame(gamePayload);
        Assertions.assertEquals(ResponseEntity.ok().body(responsePayloadDeleteSuccess),gameService.deleteGame(name));
    }

    @ParameterizedTest
    @CsvSource(value = {"cod4:2021/07/08:true", "cod5:2021/07/08:false", "cod6:2021/07/08:true"}, delimiter = ':')
    public void deleteGameFailure(String name, String date, boolean active) throws Exception {
        Assertions.assertEquals(ResponseEntity.status(404).body(responsePayloadDeleteFailure),gameService.deleteGame(name));
    }

    @Test
    public void getAllGames() throws Exception {
        CreateGamePayload gamePayload;
        Game game;
        List<Game> games = new ArrayList<>();
        for (int i=1;i<5;i++) {
            gamePayload = new CreateGamePayload("COD"+i,"2023/07/21",true);
            gameService.createGame(gamePayload);
            game = new Game("COD"+i,"2023/07/21",true);
            games.add(game);
        }

        Assertions.assertEquals(ResponseEntity.ok().body(games),gameService.getAllGames(null));

        for (int i=1;i<5;i++) {
            gameService.deleteGame("COD"+i);
        }
    }

    @Test
    public void getAllActiveGames() throws Exception {
        Random random = new Random();
        CreateGamePayload gamePayload;
        Game game;
        List<Game> games = new ArrayList<>();
        for (int i=1;i<15;i++) {
            boolean gameStatus = random.nextBoolean();
            gamePayload = new CreateGamePayload("COD"+i,"2023/07/21",gameStatus);
            gameService.createGame(gamePayload);
            if (gameStatus) {
                game = new Game("COD"+i,"2023/07/21",true);
                games.add(game);
            }
        }
        Assertions.assertEquals(ResponseEntity.ok().body(games),gameService.getAllGames("true"));

        for (int i=1;i<15;i++) {
            gameService.deleteGame("COD"+i);
        }

    }

    @Test
    public void getAllInActiveGames() throws Exception {
        Random random = new Random();
        CreateGamePayload gamePayload;
        Game game;
        List<Game> games = new ArrayList<>();
        for (int i=1;i<15;i++) {
            boolean gameStatus = random.nextBoolean();
            gamePayload = new CreateGamePayload("COD"+i,"2023/07/21",gameStatus);
            gameService.createGame(gamePayload);
            if (!gameStatus) {
                game = new Game("COD"+i,"2023/07/21", false);
                games.add(game);
            }
        }

        Assertions.assertEquals(ResponseEntity.ok().body(games),gameService.getAllGames("false"));

        for (int i=1;i<15;i++) {
            gameService.deleteGame("COD"+i);
        }
    }
}
