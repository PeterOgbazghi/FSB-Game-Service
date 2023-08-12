package com.fsb.technicalChallenge.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponsePayload {

    private final String status;
    private final String reason;

}
