package com.sudoku.sudoku_backend.controller;

import com.sudoku.sudoku_backend.persistence.NewGame;

public record PuzzleResponse(long id, CellResponse[][] cells) {

    public static PuzzleResponse from(NewGame newGame) {
        return new PuzzleResponse(newGame.id(), CellGridMapper.map(newGame.cellGrid()));
    }
}
