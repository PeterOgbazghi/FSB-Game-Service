package com.fsb.technicalChallenge.service;

import com.fsb.technicalChallenge.model.Game;
import com.fsb.technicalChallenge.pojo.CreateGamePayload;
import com.fsb.technicalChallenge.pojo.ResponsePayload;
import org.springframework.http.ResponseEntity;


import java.util.List;

public interface GameService {
     ResponseEntity<ResponsePayload> createGame(CreateGamePayload createGamePayload);
     ResponseEntity<Game> getGame(String gameName);
     ResponseEntity<ResponsePayload> deleteGame(String gameName);
     ResponseEntity<List<Game>> getAllGames(String status);
}
