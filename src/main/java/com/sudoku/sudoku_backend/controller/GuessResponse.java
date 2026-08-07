package com.sudoku.sudoku_backend.controller;

import com.sudoku.sudoku_backend.service.GuessResult;

public record GuessResponse(boolean correct, boolean solved) {

    public static GuessResponse from(GuessResult guessResult) {
        return new GuessResponse(guessResult.correct(), guessResult.solved());
    }
}
