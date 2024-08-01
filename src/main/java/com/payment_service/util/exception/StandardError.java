package com.payment_service.util.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@Setter
public class StandardError {

    private LocalDateTime time;

    private int statusCode;

    private String message;


    public StandardError(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
        this.time = LocalDateTime.now()
                .atZone(ZoneId.of("America/Sao_Paulo"))
                .toLocalDateTime();
    }
}