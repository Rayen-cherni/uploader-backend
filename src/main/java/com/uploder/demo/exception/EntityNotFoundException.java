package com.uploder.demo.exception;

import lombok.Getter;

public class EntityNotFoundException extends RuntimeException {

    @Getter
    private String errorMessage;

    public EntityNotFoundException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }
}
