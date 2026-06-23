package com.sudoku.sudoku_backend.core;

import com.sudoku.sudoku_backend.SudokuConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class SolverTest {

    private Grid grid;
    private Solver solver = new Solver();

    private static final int ROW_INDEX = 0;
    private static final int ROW_INDEX_INVALID_LOWER_BOUND = -1;
    private static final int ROW_INDEX_INVALID_UPPER_BOUND = 9;
    private static final int COL_INDEX = 0;
    private static final int COL_INDEX_INVALID_LOWER_BOUND = -1;
    private static final int COL_INDEX_INVALID_UPPER_BOUND = 9;
    private static final int VALUE = 1;
    private static final int VALUE_INVALID_LOWER_BOUND = -1;
    private static final int VALUE_INVALID_UPPER_BOUND = 10;

    @Nested
    class SolveTests {

    }

    @Nested
    class CountSolutionsTests {

    }
}
