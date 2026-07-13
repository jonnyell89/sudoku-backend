package com.sudoku.sudoku_backend.core;

import com.sudoku.sudoku_backend.SudokuConstants;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Grid {

    private final int[][] cells;

    public Grid() {
        this.cells = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    }

    public Grid(int[][] cells) {
        Validator.validateCells(cells);
        int[][] copy = new int[cells.length][];
        for (int row = 0; row < cells.length; row++) {
            copy[row] = Arrays.copyOf(cells[row], cells[row].length);
        }
        this.cells = copy;
    }

    public int[] getRow(int row) {
        Validator.validateIndex("row", row);
        return Arrays.copyOf(this.cells[row], this.cells[row].length);
    }

    public int[] getCol(int col) {
        Validator.validateIndex("col", col);
        int[] copy = new int[this.cells.length];
        for (int row = 0; row < copy.length; row++) {
            copy[row] = this.cells[row][col];
        }
        return copy;
    }

    public int[] getBox(int row, int col) {
        Validator.validateIndex("row", row);
        Validator.validateIndex("col", col);
        int[] box = new int[SudokuConstants.BOX_SIZE * SudokuConstants.BOX_SIZE];
        int startRow = Math.floorDiv(row, SudokuConstants.BOX_SIZE) * SudokuConstants.BOX_SIZE;
        int startCol = Math.floorDiv(col, SudokuConstants.BOX_SIZE) * SudokuConstants.BOX_SIZE;
        int boxIndex = 0;
        for (int boxRow = 0; boxRow < SudokuConstants.BOX_SIZE; boxRow++) {
            for (int boxCol = 0; boxCol < SudokuConstants.BOX_SIZE; boxCol++) {
                box[boxIndex++] = this.cells[startRow + boxRow][startCol + boxCol];
            }
        }
        return box;
    }

    public int getValue(int row, int col) {
        Validator.validateIndex("row", row);
        Validator.validateIndex("col", col);
        return this.cells[row][col];
    }

    public void setValue(int row, int col, int value) {
        Validator.validateIndex("row", row);
        Validator.validateIndex("col", col);
        Validator.validateValue(value);
        this.cells[row][col] = value;
    }

    public void clearValue(int row, int col) {
        Validator.validateIndex("row", row);
        Validator.validateIndex("col", col);
        this.cells[row][col] = SudokuConstants.EMPTY_CELL;
    }

    public boolean isLegal(int row, int col, int value) {
        Validator.validateIndex("row", row);
        Validator.validateIndex("col", col);
        Validator.validateValue(value);
        for (int cell : getRow(row)) {
            if (cell == value) return false;
        }
        for (int cell : getCol(col)) {
            if (cell == value) return false;
        }
        for (int cell : getBox(row, col)) {
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

    public boolean isValid() {
        for (int index = 0; index < this.cells.length; index++) {
            if (hasDuplicates(getRow(index))) return false;
            if (hasDuplicates(getCol(index))) return false;
        }
        for (int row = 0; row < this.cells.length; row += SudokuConstants.BOX_SIZE) {
            for (int col = 0; col < this.cells[row].length; col += SudokuConstants.BOX_SIZE) {
                if (hasDuplicates(getBox(row, col))) return false;
            }
        }
        return true;
    }

    public void validate() {
        if (!isValid()) {
            throw new IllegalArgumentException("grid must not contain duplicate values in any row, col, or box.");
        }
    }

    public Grid copy() {
        return new Grid(this.cells);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Grid grid = (Grid) object;
        return Objects.deepEquals(cells, grid.cells);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(cells);
    }

    private boolean hasDuplicates(int[] unit) {
        Set<Integer> seen = new HashSet<>();
        for (int cell : unit) {
            if (cell != SudokuConstants.EMPTY_CELL && !seen.add(cell)) {
                return true;
            }
        }
        return false;
    }
}
