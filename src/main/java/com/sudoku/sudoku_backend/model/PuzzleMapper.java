package com.sudoku.sudoku_backend.model;

import com.sudoku.sudoku_backend.SudokuConstants;
import com.sudoku.sudoku_backend.core.Grid;
import com.sudoku.sudoku_backend.core.Puzzle;

public class PuzzleMapper {

    public static CellGrid map(Puzzle puzzle) {
        if (puzzle == null) {
            throw new IllegalArgumentException("puzzle must not be null.");
        }
        Cell[][] cells = new Cell[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
        Grid carved = puzzle.carved();
        for (int row = 0; row < SudokuConstants.GRID_SIZE; row++) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; col++) {
                int value = carved.getValue(row, col);
                Cell cell = value == 0
                        ? Cell.empty(row, col)
                        : Cell.given(row, col, value);
                cells[row][col] = cell;
            }
        }
        return new CellGrid(cells);
    }
}
