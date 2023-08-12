package com.fsb.technicalChallenge.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fsb.technicalChallenge.model.Game;
import com.fsb.technicalChallenge.pojo.CreateGamePayload;
import com.fsb.technicalChallenge.pojo.ResponsePayload;
import com.fsb.technicalChallenge.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    GameService gameService;
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

        ResultActions createGameResult = mockMvc.perform(post("/api/v1/creategame")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectToJson(gamePayload))));

        createGameResult.andExpect(status().isOk()).andExpect(content().json(objectToJson(responsePayloadsuccess)));
    }

    @Test
    public void createGamefail() throws Exception {
        CreateGamePayload gamePayload = new CreateGamePayload("COD", "2023/07/15" , true);

        gameService.createGame(gamePayload);
        ResultActions createGameResult = mockMvc.perform(post("/api/v1/creategame")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectToJson(gamePayload))));

        createGameResult.andExpect(status().is(400)).andExpect(content().json(objectToJson(responsePayloadFailure)));
    }

    @ParameterizedTest
    @CsvSource(value = {"cod:2021/07/08:true", "cod2:2021/07/08:false", "cod3:2021/07/08:true"}, delimiter = ':')
    public void getGameSuccess(String name, String date, boolean active) throws Exception {
        CreateGamePayload gamePayload = new CreateGamePayload(name,date,active);
        Game game = new Game(name,date,active);
        gameService.createGame(gamePayload);
        ResultActions getGameResult = mockMvc.perform(get("/api/v1/getgame/" + name));

        getGameResult.andExpect(status().is(200)).andExpect(content().json(objectToJson(game)));
    }

    @ParameterizedTest
    @CsvSource(value = {"cod:2021/07/08:true", "cod2:2021/07/08:false", "cod3:2021/07/08:true"}, delimiter = ':')
    public void getGameFailure(String name, String date, boolean active) throws Exception {
        ResultActions getGameResult = mockMvc.perform(get("/api/v1/getgame/" + name));
        getGameResult.andExpect(status().is(204));
    }

    @ParameterizedTest
    @CsvSource(value = {"cod:2021/07/08:true", "cod2:2021/07/08:false", "cod3:2021/07/08:true"}, delimiter = ':')
    public void deleteGameSuccess(String name, String date, boolean active) throws Exception {
        CreateGamePayload gamePayload = new CreateGamePayload(name,date,active);
        gameService.createGame(gamePayload);
        ResultActions deleteGameResult = mockMvc.perform(delete("/api/v1/deletegame/" + name));
        deleteGameResult.andExpect(status().is(200)).andExpect(content().json(objectToJson(responsePayloadDeleteSuccess)));
    }

    @ParameterizedTest
    @CsvSource(value = {"cod4:2021/07/08:true", "cod5:2021/07/08:false", "cod6:2021/07/08:true"}, delimiter = ':')
    public void deleteGameFailure(String name, String date, boolean active) throws Exception {
        ResultActions deleteGameResult = mockMvc.perform(delete("/api/v1/deletegame/" + name));
        deleteGameResult.andExpect(status().is(404)).andExpect(content().json(objectToJson(responsePayloadDeleteFailure)));
    }

    @Test
    public void getAllActiveGames() throws Exception {
        CreateGamePayload gamePayload;
        Game game;
        List<Game> games = new ArrayList<>();
        for (int i=1;i<5;i++) {
            gamePayload = new CreateGamePayload("COD"+i,"2023/07/21",true);
            gameService.createGame(gamePayload);
            game = new Game("COD"+i,"2023/07/21",true);
            games.add(game);
        }

        ResultActions getGamesResult = mockMvc.perform(get("/api/v1/getallgames/true"));

        getGamesResult.andExpect(status().is(200)).andExpect(content().json(objectToJson(games)));
    }





    private String objectToJson(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            fail("Failed to convert object to json");
            return null;
        }
    }





}
