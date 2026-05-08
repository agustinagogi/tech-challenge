package com.agustina.library.exception;

public class DuplicateActiveLoanException extends RuntimeException {
    public DuplicateActiveLoanException(String message) {
        super(message);
    }
}
