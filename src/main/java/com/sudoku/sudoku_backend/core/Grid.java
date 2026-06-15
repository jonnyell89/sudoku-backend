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
        }
    }
}
