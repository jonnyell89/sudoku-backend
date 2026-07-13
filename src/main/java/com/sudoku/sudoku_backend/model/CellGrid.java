package com.sudoku.sudoku_backend.model;

public class CellGrid {

    private final Cell[][] cells;

    public CellGrid(Cell[][] cells) {
        this.cells = cells;
    }

    public Cell getCell(int row, int col) {
        return cells[row][col];
    }
}
