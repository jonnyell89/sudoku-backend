package com.sudoku.sudoku_backend.persistence;

import com.sudoku.sudoku_backend.model.CellGrid;

public record NewGame(Long id, CellGrid cellGrid) {}
