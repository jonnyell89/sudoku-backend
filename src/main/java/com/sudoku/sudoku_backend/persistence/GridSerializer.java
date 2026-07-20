package com.sudoku.sudoku_backend.persistence;

import com.sudoku.sudoku_backend.SudokuConstants;
import com.sudoku.sudoku_backend.core.Grid;

import java.util.Objects;

public class GridSerializer {

    public static String serialize(Grid grid) {
        Objects.requireNonNull(grid, "grid must not be null.");
        grid.validate();
        StringBuilder stringBuilder = new StringBuilder(SudokuConstants.GRID_CELLS);
        for (int row = 0; row < SudokuConstants.GRID_SIZE; row++) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; col++) {
                int value = grid.getValue(row, col);
                stringBuilder.append(value);
            }
        }
        return stringBuilder.toString();
    }
}
