package com.rubenzu03.beatbank.application.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SongNotFoundException extends RuntimeException {
    private final HttpStatus status;
    public SongNotFoundException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}
