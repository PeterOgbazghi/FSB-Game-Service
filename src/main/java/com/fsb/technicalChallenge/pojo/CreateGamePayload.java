package com.fsb.technicalChallenge.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateGamePayload {
    private final String name;
    private final String dateOfCreation;
    private boolean active;
}
