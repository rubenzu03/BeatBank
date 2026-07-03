package com.rubenzu03.beatbank.application.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResourceNotFoundException extends RuntimeException {
    private final HttpStatus status;

    public ResourceNotFoundException(String resource, Long id) {
        super(resource + " with id " + id + " not found");
        this.status = HttpStatus.NOT_FOUND;
    }

}
