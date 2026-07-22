package com.sudoku.sudoku_backend.persistence;

import com.sudoku.sudoku_backend.model.CellGrid;

// user request -> controller -> service -> newPuzzle -> NewGame -> controller -> user response
public record NewGame(Long id, CellGrid cellGrid) {}
