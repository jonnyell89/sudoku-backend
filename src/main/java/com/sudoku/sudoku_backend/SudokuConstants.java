package com.sudoku.sudoku_backend;

public final class SudokuConstants {

    private SudokuConstants() {}

    public static final int GRID_SIZE = 9;
    public static final int GRID_CELLS = GRID_SIZE * GRID_SIZE;
    public static final int BOX_SIZE = 3;
    public static final int EMPTY_CELL = 0;
    public static final int MIN_INDEX = 0;
    public static final int MAX_INDEX = GRID_SIZE - 1;
    public static final int MIN_VALUE = 1;
    public static final int MAX_VALUE = GRID_SIZE;
}
