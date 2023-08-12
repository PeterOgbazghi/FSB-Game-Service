package com.fsb.technicalChallenge.service;

import com.fsb.technicalChallenge.model.Game;
import com.fsb.technicalChallenge.pojo.CreateGamePayload;
import com.fsb.technicalChallenge.pojo.ResponsePayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
@Service
public class GameServiceImpl implements GameService {

    private CopyOnWriteArrayList<String> existingGameNames = new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<Game> games = new CopyOnWriteArrayList<>();
    private static Logger logger = LoggerFactory.getLogger(GameServiceImpl.class);


    @Override
    public ResponseEntity<ResponsePayload> createGame(CreateGamePayload createGamePayload) {
        if (existingGameNames.contains(createGamePayload.getName())) {
            logger.info("Game creation Failed: game already exists!");
            return ResponseEntity.status(400).body(new ResponsePayload("FAILED","Game already exists"));
        } else {
            existingGameNames.add(createGamePayload.getName());
            Game game = new Game(createGamePayload.getName(),createGamePayload.getDateOfCreation(),createGamePayload.isActive());
            games.add(game);
            logger.info("Game creation Successful!");
            return ResponseEntity.status(200).body(new ResponsePayload("SUCCESS","Game created!"));
        }
    }

    @Override
    public ResponseEntity<Game> getGame(String gameName) {
        Game requestedGame = null;
        if (!existingGameNames.contains(gameName)) {
            logger.info("Game not found!");
            return ResponseEntity.status(204).body(null);
        } else {
            for (Game game : games) {
                if (game.getName().equals(gameName)) {
                    logger.info("Game found!!");
                    requestedGame = new Game(game.getName(),game.getDateOfCreation(),game.isActive());
                    break;
                }
            }
            return ResponseEntity.ok(requestedGame);
        }

    }

    @Override
    public ResponseEntity<ResponsePayload> deleteGame(String gameName) {
        Game requestedGame = null;
        if (!existingGameNames.contains(gameName)) {
            logger.info("Game doesn't exist: Deletion Failed!");
            return ResponseEntity.status(404).body(new ResponsePayload("FAILED","Game doesn't exist!"));
        } else {
            for (Game game : games) {
                if (game.getName().equals(gameName)) {
                    logger.info("Game found!!");
                    requestedGame = new Game(game.getName(),game.getDateOfCreation(),game.isActive());
                    games.remove(requestedGame);
                    existingGameNames.remove(gameName);
                    logger.info("Game deleted successfully!!");
                    break;
                }
            }
            return ResponseEntity.ok(new ResponsePayload("SUCCESS","Game deleted successfully"));
        }
    }

    @Override
    public ResponseEntity<List<Game>> getAllGames(String status) {
        List<Game> gamesToReturn = new ArrayList<>();
        if (existingGameNames.isEmpty()) {
            logger.info("No Games available!");
            return ResponseEntity.status(204).body(null);
        } else {
            if (status == null) {
                logger.info("Getting all games from Cache");
                games.stream().forEach(g -> {
                    Game gameFromLsit = new Game(g.getName(),g.getDateOfCreation(),g.isActive());
                    gamesToReturn.add(gameFromLsit);

                });
                return ResponseEntity.ok(gamesToReturn);
            } else {
                logger.info("Getting all games from Cache with active status " + Boolean.valueOf(status));
                games.stream()
                     .filter(g -> g.isActive()== Boolean.valueOf(status))
                     .forEach(g ->{
                         Game gameFromLsit = new Game(g.getName(),g.getDateOfCreation(),g.isActive());
                         gamesToReturn.add(gameFromLsit);
                     });
                if (gamesToReturn.isEmpty()) {
                    logger.info("No games found with active status " + Boolean.valueOf(status));
                    return ResponseEntity.status(204).body(gamesToReturn);
                } else {
                    return ResponseEntity.ok(gamesToReturn);
                }
            }
        }
    }


}
