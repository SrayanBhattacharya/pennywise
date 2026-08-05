package com.pennywise.backend.common.exception;

public class InvalidImportStateException extends RuntimeException {
    public InvalidImportStateException(String message) {
        super(message);
    }
}
