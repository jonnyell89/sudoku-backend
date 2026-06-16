package com.sudoku.sudoku_backend.core;

import com.sudoku.sudoku_backend.SudokuConstants;

public class Grid {

    private final int[][] cells;

    public Grid() {
        this.cells = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    }

    public Grid(int[][] cells) {
        validateCells(cells);
        int[][] copy = new int[cells.length][];
        for (int i = 0; i < cells.length; i++) {
            copy[i] = cells[i].clone();
        }
        this.cells = copy;
    }

    public int[] getRow(int rowIndex) {
        validateRowIndex(rowIndex);
        return this.cells[rowIndex].clone();
    }

    public int[] getCol(int colIndex) {
        validateColIndex(colIndex);
        int[] col = new int[this.cells.length];
        for (int i = 0; i < col.length; i++) {
            col[i] = this.cells[i][colIndex];
        }
        return col;
    }

    private void validateCells(int[][] cells) {
        if (cells == null) {
            throw new IllegalArgumentException("cells should not be null.");
        }
        if (cells.length != SudokuConstants.GRID_SIZE) {
            throw new IllegalArgumentException(String.format("cells should contain %d rows.", SudokuConstants.GRID_SIZE));
        }
        for (int i = 0; i < cells.length; i++) {
            if (cells[i] == null) {
                throw new IllegalArgumentException(String.format("Row %d should not be null.", i));
            }
            if (cells[i].length != SudokuConstants.GRID_SIZE) {
                throw new IllegalArgumentException(String.format("Row %d should contain %d cells.", i, SudokuConstants.GRID_SIZE));
            }
            for (int j = 0; j < cells[i].length; j++) {
                validateCell(cells[i][j]);
            }
        }
    }

    private void validateCell(int cell) {
        if (cell < SudokuConstants.EMPTY_CELL || cell > SudokuConstants.MAX_VALUE) {
            throw new IllegalArgumentException(String.format("cell must be between %d and %d.", SudokuConstants.EMPTY_CELL, SudokuConstants.MAX_VALUE));
        }
    }

    private void validateRowIndex(int rowIndex) {
        if (rowIndex < SudokuConstants.MIN_INDEX || rowIndex > SudokuConstants.MAX_INDEX) {
            throw new IllegalArgumentException(String.format("rowIndex must be between %d and %d.", SudokuConstants.MIN_INDEX, SudokuConstants.MAX_INDEX));
        }
    }

    private void validateColIndex(int colIndex) {
        if (colIndex < SudokuConstants.MIN_INDEX || colIndex > SudokuConstants.MAX_INDEX) {
            throw new IllegalArgumentException(String.format("colIndex must be between %d and %d.", SudokuConstants.MIN_INDEX, SudokuConstants.MAX_INDEX));
        }
    }
}
