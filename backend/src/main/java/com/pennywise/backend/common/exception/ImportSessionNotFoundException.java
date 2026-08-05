package com.pennywise.backend.common.exception;

public class ImportSessionNotFoundException extends RuntimeException {
    public ImportSessionNotFoundException(String message) {
        super(message);
    }
}
