package com.sudoku.sudoku_backend.controller;

import com.sudoku.sudoku_backend.service.NewPuzzle;

public record PuzzleResponse(long id, CellResponse[][] cells) {

    public static PuzzleResponse from(NewPuzzle newPuzzle) {
        return new PuzzleResponse(newPuzzle.id(), CellGridMapper.map(newPuzzle.cellGrid()));
    }
}
