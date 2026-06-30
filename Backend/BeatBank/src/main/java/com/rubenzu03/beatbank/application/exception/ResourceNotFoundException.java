package com.rubenzu03.beatbank.application.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends RuntimeException {
    private final HttpStatus status;

    public ResourceNotFoundException(String resource, Long id) {
        super(resource + " with id " + id + " not found");
        this.status = HttpStatus.NOT_FOUND;
    }

    public ResourceNotFoundException(String message) {
        super(message);
        this.status = HttpStatus.NOT_FOUND;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
