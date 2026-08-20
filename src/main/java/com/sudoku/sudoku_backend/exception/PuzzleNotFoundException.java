package com.sudoku.sudoku_backend.exception;

public class PuzzleNotFoundException extends RuntimeException {

    public PuzzleNotFoundException(String message) {
        super(message);
    }
}
