package com.sudoku.sudoku_backend.model;

import com.sudoku.sudoku_backend.core.Validator;

public class CellGrid {

    private final Cell[][] cells;

    public CellGrid(Cell[][] cells) {
        Validator.validateCells(cells);
        this.cells = cells;
    }

    public Cell getCell(int row, int col) {
        return cells[row][col];
    }
}
