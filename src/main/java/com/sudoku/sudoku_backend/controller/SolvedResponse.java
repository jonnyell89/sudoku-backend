package com.sudoku.sudoku_backend.controller;

import com.sudoku.sudoku_backend.service.SolvedResult;

public record SolvedResponse(boolean solved) {

    public static SolvedResponse from(SolvedResult solvedResult) {
        return new SolvedResponse(solvedResult.solved());
    }
}
