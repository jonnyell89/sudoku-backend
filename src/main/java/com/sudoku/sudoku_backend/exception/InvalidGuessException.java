package com.sudoku.sudoku_backend.exception;

public class InvalidGuessException extends RuntimeException {

    public InvalidGuessException(String message, IllegalArgumentException exception) {
        super(message, exception);
    }
}
