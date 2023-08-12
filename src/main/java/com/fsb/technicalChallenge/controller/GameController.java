package com.fsb.technicalChallenge.controller;

import com.fsb.technicalChallenge.model.Game;
import com.fsb.technicalChallenge.pojo.CreateGamePayload;
import com.fsb.technicalChallenge.pojo.ResponsePayload;
import com.fsb.technicalChallenge.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GameController {

    @Autowired
    private GameService gameService;

    @PostMapping(value = "/api/v1/creategame")
    public ResponseEntity<ResponsePayload> createGame(@RequestBody CreateGamePayload createGamePayload) {
        return gameService.createGame(createGamePayload);
    }

    @GetMapping("/api/v1/getgame/{gamename}")
    public ResponseEntity<Game> getGame(@PathVariable("gamename") String gameName) {
        return gameService.getGame(gameName);

    }

    @DeleteMapping("/api/v1/deletegame/{gamename}")
    public ResponseEntity<ResponsePayload> deleteGame(@PathVariable("gamename") String gameName) {
        return gameService.deleteGame(gameName);
    }

    @GetMapping(value = {"/api/v1/getallgames/{status}", "/api/v1/getallgames"})
    public ResponseEntity<List<Game>> getAllGames(@PathVariable(required = false) String status) {
        return gameService.getAllGames(status);

    }
}
