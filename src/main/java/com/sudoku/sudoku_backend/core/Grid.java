package com.sudoku.sudoku_backend.core;

import com.sudoku.sudoku_backend.SudokuConstants;

import java.util.Arrays;
import java.util.Objects;

public class Grid {

    private final int[][] cells;

    public Grid() {
        this.cells = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    }

    public Grid(int[][] cells) {
        validateCells(cells);
        int[][] copy = new int[cells.length][];
        for (int i = 0; i < cells.length; i++) {
            copy[i] = Arrays.copyOf(cells[i], cells[i].length);
        }
        this.cells = copy;
    }

    public int[] getRow(int rowIndex) {
        validateRowIndex(rowIndex);
        return Arrays.copyOf(this.cells[rowIndex], this.cells[rowIndex].length);
    }

    public int[] getCol(int colIndex) {
        validateColIndex(colIndex);
        int[] col = new int[this.cells.length];
        for (int i = 0; i < col.length; i++) {
            col[i] = this.cells[i][colIndex];
        }
        return col;
    }

    public int[] getBox(int rowIndex, int colIndex) {
        validateIndices(rowIndex, colIndex);
        int[] box = new int[SudokuConstants.BOX_SIZE * SudokuConstants.BOX_SIZE];
        int startRow = Math.floorDiv(rowIndex, SudokuConstants.BOX_SIZE) * SudokuConstants.BOX_SIZE;
        int startCol = Math.floorDiv(colIndex, SudokuConstants.BOX_SIZE) * SudokuConstants.BOX_SIZE;
        int boxIndex = 0;
        for (int i = 0; i < SudokuConstants.BOX_SIZE; i++) {
            for (int j = 0; j < SudokuConstants.BOX_SIZE; j++) {
                box[boxIndex++] = this.cells[startRow + i][startCol + j];
            }
        }
        return box;
    }

    public int getValue(int rowIndex, int colIndex) {
        validateIndices(rowIndex, colIndex);
        return this.cells[rowIndex][colIndex];
    }

    public void setValue(int rowIndex, int colIndex, int value) {
        validateIndices(rowIndex, colIndex);
        validateCell(value);
        this.cells[rowIndex][colIndex] = value;
    }

    public void clearValue(int rowIndex, int colIndex) {
        validateIndices(rowIndex, colIndex);
        this.cells[rowIndex][colIndex] = SudokuConstants.EMPTY_CELL;
    }

    public boolean isLegal(int rowIndex, int colIndex, int value) {
        validateIndices(rowIndex, colIndex);
        validateCell(value);
        for (int cell : getRow(rowIndex)) {
            if (cell == value) return false;
        }
        for (int cell : getCol(colIndex)) {
            if (cell == value) return false;
        }
        for (int cell : getBox(rowIndex, colIndex)) {
            if (cell == value) return false;
        }
        return true;
    }

    public boolean isFull() {
        for (int[] row : this.cells) {
            for (int cell : row) {
                if (cell == SudokuConstants.EMPTY_CELL) {
                    return false;
                }
            }
        }
        return true;
    }

    public Grid copy() {
        return new Grid(this.cells);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grid grid = (Grid) o;
        return Objects.deepEquals(cells, grid.cells);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(cells);
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

    private void validateIndices(int rowIndex, int colIndex) {
        validateRowIndex(rowIndex);
        validateColIndex(colIndex);
    }
}
