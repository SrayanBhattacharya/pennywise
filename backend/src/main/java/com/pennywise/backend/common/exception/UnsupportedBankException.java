package com.pennywise.backend.common.exception;

public class UnsupportedBankException extends RuntimeException {
    public UnsupportedBankException(String message) {
        super(message);
    }
}
