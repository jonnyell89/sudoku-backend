package com.sudoku.sudoku_backend.core;

import com.sudoku.sudoku_backend.SudokuConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class GridTest {

    private Grid grid;

    private static final int ROW_INDEX = 0;
    private static final int ROW_INDEX_INVALID_LOWER_BOUND = -1;
    private static final int ROW_INDEX_INVALID_UPPER_BOUND = 9;
    private static final int COL_INDEX = 0;
    private static final int COL_INDEX_INVALID_LOWER_BOUND = -1;
    private static final int COL_INDEX_INVALID_UPPER_BOUND = 9;
    private static final int VALUE = 1;
    private static final int VALUE_INVALID_LOWER_BOUND = -1;
    private static final int VALUE_INVALID_UPPER_BOUND = 10;

    private static int[][] createValidCells() {
        int[][] cells = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
//        for (int i = 0; i < cells.length; i++) {
//            Arrays.fill(cells[i], VALUE);
//        }
        for (int[] row : cells) {
            Arrays.fill(row, VALUE);
        }
        return cells;
    }

    @Nested
    class DefaultConstructorTests {

        @BeforeEach
        void init() {
            grid = new Grid();
        }

        @Test
        void shouldInitialiseAllCellsToEmptyValue() {
//            for (int i = 0; i < SudokuConstants.GRID_SIZE; i++) {
//                for (int cell = 0; cell < grid.getRow(i).length; cell++) {
//                    assertEquals(SudokuConstants.EMPTY_CELL, cell);
//                }
//            }
            for (int i = 0; i < SudokuConstants.GRID_SIZE; i++) {
                for (int cell : grid.getRow(i)) {
                    assertEquals(SudokuConstants.EMPTY_CELL, cell);
                }
            }
        }
    }

    @Nested
    class ParameterisedConstructorTests {

        @BeforeEach
        void init() {
            grid = new Grid(createValidCells());
        }

        @Test
        void shouldAcceptValidCells() {
            assertDoesNotThrow(() -> new Grid(createValidCells()));
        }

        @Test
        void shouldStoreCorrectValues() {
//            for (int i = 0; i < SudokuConstants.GRID_SIZE; i++) {
//                for (int cell = 0; cell < grid.getRow(i).length; cell++) {
//                    assertEquals(VALUE, cell);
//                }
//            }
            for (int i = 0; i < SudokuConstants.GRID_SIZE; i++) {
                for (int cell : grid.getRow(i)) {
                    assertEquals(VALUE, cell);
                }
            }
        }

        @Test
        void shouldDeepCopyCells() {
            int[][] cells = createValidCells();
            Grid copiedGrid = new Grid(cells);
            cells[ROW_INDEX][COL_INDEX] = VALUE + 1;
            assertEquals(VALUE, copiedGrid.getRow(ROW_INDEX)[COL_INDEX]);
        }

        @Test
        void shouldThrowWhenCellsIsNull() {
            assertThrows(IllegalArgumentException.class, () -> new Grid(null));
        }

        @Test
        void shouldThrowWhenCellsHasTooFewRows() {
            int[][] cells = new int[SudokuConstants.GRID_SIZE - 1][SudokuConstants.GRID_SIZE];
            assertThrows(IllegalArgumentException.class, () -> new Grid(cells));
        }

        @Test
        void shouldThrowWhenCellsHasTooManyRows() {
            int[][] cells = new int[SudokuConstants.GRID_SIZE + 1][SudokuConstants.GRID_SIZE];
            assertThrows(IllegalArgumentException.class, () -> new Grid(cells));
        }

        @Test
        void shouldThrowWhenRowIsNull() {
            int[][] cells = createValidCells();
            cells[ROW_INDEX] = null;
            assertThrows(IllegalArgumentException.class, () -> new Grid(cells));
        }

        @Test
        void shouldThrowWhenRowHasTooFewCells() {
            int[][] cells = createValidCells();
            cells[ROW_INDEX] = new int[SudokuConstants.GRID_SIZE - 1];
            assertThrows(IllegalArgumentException.class, () -> new Grid(cells));
        }

        @Test
        void shouldThrowWhenRowHasTooManyCells() {
            int[][] cells = createValidCells();
            cells[ROW_INDEX] = new int[SudokuConstants.GRID_SIZE + 1];
            assertThrows(IllegalArgumentException.class, () -> new Grid(cells));
        }

        @Test
        void shouldThrowWhenCellValueIsBelowLowerBound() {
            int[][] cells = createValidCells();
            cells[ROW_INDEX][COL_INDEX] = VALUE_INVALID_LOWER_BOUND;
            assertThrows(IllegalArgumentException.class, () -> new Grid(cells));
        }

        @Test
        void shouldThrowWhenCellValueIsAboveUpperBound() {
            int[][] cells = createValidCells();
            cells[ROW_INDEX][COL_INDEX] = VALUE_INVALID_UPPER_BOUND;
            assertThrows(IllegalArgumentException.class, () -> new Grid(cells));
        }
    }

    @Nested
    class GetRowTests {

        @BeforeEach
        void init() {
            grid = new Grid(createValidCells());
        }

        @Test
        void shouldReturnRowOfCorrectLength() {
            for (int i = 0; i < SudokuConstants.GRID_SIZE; i++) {
                assertEquals(SudokuConstants.GRID_SIZE, grid.getRow(i).length);
            }
        }

        @Test
        void shouldThrowWhenRowIndexIsBelowLowerBound() {
            assertThrows(IllegalArgumentException.class, () -> grid.getRow(ROW_INDEX_INVALID_LOWER_BOUND));
        }

        @Test
        void shouldThrowWhenRowIndexIsAboveUpperBound() {
            assertThrows(IllegalArgumentException.class, () -> grid.getRow(ROW_INDEX_INVALID_UPPER_BOUND));
        }

        @Test
        void shouldReturnDefensiveCopy() {
            int[] copiedRow = grid.getRow(ROW_INDEX);
            copiedRow[COL_INDEX] = VALUE + 1;
            assertEquals(VALUE, grid.getRow(ROW_INDEX)[COL_INDEX]);
        }
    }
}
