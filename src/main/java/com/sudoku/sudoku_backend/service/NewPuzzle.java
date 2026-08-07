package com.sudoku.sudoku_backend.service;

import com.sudoku.sudoku_backend.model.CellGrid;

public record NewPuzzle(Long id, CellGrid cellGrid) {}
