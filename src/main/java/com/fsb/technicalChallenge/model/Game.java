package com.fsb.technicalChallenge.model;

import lombok.Data;

@Data
public class Game {
    private final String name;
    private final String dateOfCreation;
    private boolean active;


    public Game(String name, String dateOfCreation, Boolean active) {
        this.name = name;
        this.dateOfCreation = dateOfCreation;
        this.active = active;
    }
}
