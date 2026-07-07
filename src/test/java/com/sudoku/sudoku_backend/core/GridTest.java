package com.sudoku.sudoku_backend.core;

import com.sudoku.sudoku_backend.SudokuConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class GridTest {

    private Grid grid;

    private static final int ROW = 0;
    private static final int BELOW_MIN_ROW = -1;
    private static final int ABOVE_MAX_ROW = 9;
    private static final int COL = 0;
    private static final int BELOW_MIN_COL = -1;
    private static final int ABOVE_MAX_COL = 9;
    private static final int VALUE = 1;
    private static final int BELOW_MIN_VALUE = -1;
    private static final int ABOVE_MAX_VALUE = 10;

    private static int[][] createCells() {
        int[][] cells = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
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
        void shouldContainEmptyCellsOnly() {
            for (int row = 0; row < SudokuConstants.GRID_SIZE; row++) {
                for (int cell : grid.getRow(row)) {
                    assertEquals(SudokuConstants.EMPTY_CELL, cell);
                }
            }
        }
    }

    @Nested
    class ParameterisedConstructorTests {

        @BeforeEach
        void init() {
            grid = new Grid(createCells());
        }

        @Test
        void shouldAcceptCells() {
            assertDoesNotThrow(() -> new Grid(createCells()));
        }

        @Test
        void shouldContainCorrectValues() {
            for (int row = 0; row < SudokuConstants.GRID_SIZE; row++) {
                for (int cell : grid.getRow(row)) {
                    assertEquals(VALUE, cell);
                }
            }
        }

        @Test
        void shouldDeepCopyCells() {
            int[][] cells = createCells();
            Grid copy = new Grid(cells);
            cells[ROW][COL] = VALUE + 1;
            assertEquals(VALUE, copy.getRow(ROW)[COL]);
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
            int[][] cells = createCells();
            cells[ROW] = null;
            assertThrows(IllegalArgumentException.class, () -> new Grid(cells));
        }

        @Test
        void shouldThrowWhenRowHasTooFewCells() {
            int[][] cells = createCells();
            cells[ROW] = new int[SudokuConstants.GRID_SIZE - 1];
            assertThrows(IllegalArgumentException.class, () -> new Grid(cells));
        }

        @Test
        void shouldThrowWhenRowHasTooManyCells() {
            int[][] cells = createCells();
            cells[ROW] = new int[SudokuConstants.GRID_SIZE + 1];
            assertThrows(IllegalArgumentException.class, () -> new Grid(cells));
        }

        @Test
        void shouldThrowWhenValueIsBelowMin() {
            int[][] cells = createCells();
            cells[ROW][COL] = BELOW_MIN_VALUE;
            assertThrows(IllegalArgumentException.class, () -> new Grid(cells));
        }

        @Test
        void shouldThrowWhenValueIsAboveMax() {
            int[][] cells = createCells();
            cells[ROW][COL] = ABOVE_MAX_VALUE;
            assertThrows(IllegalArgumentException.class, () -> new Grid(cells));
        }
    }

    @Nested
    class GetRowTests {

        @BeforeEach
        void init() {
            grid = new Grid(createCells());
        }

        @Test
        void shouldReturnRowWithCorrectLength() {
            for (int row = 0; row < SudokuConstants.GRID_SIZE; row++) {
                assertEquals(SudokuConstants.GRID_SIZE, grid.getRow(row).length);
            }
        }

        @Test
        void shouldThrowWhenRowIsBelowMin() {
            assertThrows(IllegalArgumentException.class, () -> grid.getRow(BELOW_MIN_ROW));
        }

        @Test
        void shouldThrowWhenRowIsAboveMax() {
            assertThrows(IllegalArgumentException.class, () -> grid.getRow(ABOVE_MAX_ROW));
        }

        @Test
        void shouldReturnDefensiveCopy() {
            int[] copy = grid.getRow(ROW);
            copy[COL] = VALUE + 1;
            assertEquals(VALUE, grid.getRow(ROW)[COL]);
        }
    }

    @Nested
    class GetColTests {

        @BeforeEach
        void init() {
            grid = new Grid(createCells());
        }

        @Test
        void shouldReturnColWithCorrectLength() {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; col++) {
                assertEquals(SudokuConstants.GRID_SIZE, grid.getCol(col).length);
            }
        }

        @Test
        void shouldThrowWhenColIsBelowMin() {
            assertThrows(IllegalArgumentException.class, () -> grid.getCol(BELOW_MIN_COL));
        }

        @Test
        void shouldThrowWhenColIsAboveMax() {
            assertThrows(IllegalArgumentException.class, () -> grid.getCol(ABOVE_MAX_COL));
        }

        @Test
        void shouldReturnDefensiveCopy() {
            int[] copy = grid.getCol(COL);
            copy[ROW] = VALUE + 1;
            assertEquals(VALUE, grid.getCol(COL)[ROW]);
        }
    }

    @Nested
    class GetBoxTests {

        @BeforeEach
        void init() {
            grid = new Grid(createCells());
        }

        @Test
        void shouldReturnBoxWithCorrectLength() {
            for (int row = 0; row < SudokuConstants.GRID_SIZE; row++) {
                for (int col = 0; col < SudokuConstants.GRID_SIZE; col++) {
                    assertEquals(SudokuConstants.GRID_SIZE, grid.getBox(row, col).length);
                }
            }
        }

        @Test
        void shouldThrowWhenRowIsBelowMin() {
            assertThrows(IllegalArgumentException.class, () -> grid.getBox(BELOW_MIN_ROW, COL));
        }

        @Test
        void shouldThrowWhenRowIsAboveMax() {
            assertThrows(IllegalArgumentException.class, () -> grid.getBox(ABOVE_MAX_ROW, COL));
        }

        @Test
        void shouldThrowWhenColIsBelowMin() {
            assertThrows(IllegalArgumentException.class, () -> grid.getBox(ROW, BELOW_MIN_COL));
        }

        @Test
        void shouldThrowWhenColIsAboveMax() {
            assertThrows(IllegalArgumentException.class, () -> grid.getBox(ROW, ABOVE_MAX_COL));
        }

        @Test
        void shouldReturnBoxCellsInCorrectOrder() {
            int[][] cells = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
            cells[0][0] = 1; cells[0][1] = 2; cells[0][2] = 3;
            cells[1][0] = 4; cells[1][1] = 5; cells[1][2] = 6;
            cells[2][0] = 7; cells[2][1] = 8; cells[2][2] = 9;
            grid = new Grid(cells);
            assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, grid.getBox(ROW, COL));
        }

        @Test
        void shouldReturnBoxCells() {
            int[][] cells = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
            cells[6][6] = 1; cells[6][7] = 2; cells[6][8] = 3;
            cells[7][6] = 4; cells[7][7] = 5; cells[7][8] = 6;
            cells[8][6] = 7; cells[8][7] = 8; cells[8][8] = 9;
            grid = new Grid(cells);
            for (int boxRow = 0; boxRow < SudokuConstants.GRID_SIZE; boxRow += SudokuConstants.BOX_SIZE) {
                for (int boxCol = 0; boxCol < SudokuConstants.GRID_SIZE; boxCol += SudokuConstants.BOX_SIZE) {
                    if (boxRow == 6 && boxCol == 6) {
                        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, grid.getBox(boxRow, boxCol));
                    } else {
                        assertArrayEquals(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0}, grid.getBox(boxRow, boxCol));
                    }
                }
            }
        }

        @Test
        void shouldReturnDefensiveCopy() {
            int[] copy = grid.getBox(ROW, COL);
            copy[ROW] = VALUE + 1;
            assertEquals(VALUE, grid.getBox(ROW, COL)[ROW]);
        }
    }

    @Nested
    class GetValueTests {

        @BeforeEach
        void init() {
            grid = new Grid(createCells());
        }

        @Test
        void shouldReturnCorrectValue() {
            assertEquals(VALUE, grid.getValue(ROW, COL));
        }

        @Test
        void shouldThrowWhenRowIsBelowMin() {
            assertThrows(IllegalArgumentException.class, () -> grid.getValue(BELOW_MIN_ROW, COL));
        }

        @Test
        void shouldThrowWhenRowIsAboveMax() {
            assertThrows(IllegalArgumentException.class, () -> grid.getValue(ABOVE_MAX_ROW, COL));
        }

        @Test
        void shouldThrowWhenColIsBelowMin() {
            assertThrows(IllegalArgumentException.class, () -> grid.getValue(ROW, BELOW_MIN_COL));
        }

        @Test
        void shouldThrowWhenColIsAboveMax() {
            assertThrows(IllegalArgumentException.class, () -> grid.getValue(ROW, ABOVE_MAX_COL));
        }
    }

    @Nested
    class SetValueTests {

        @BeforeEach
        void init() {
            grid = new Grid(createCells());
        }

        @Test
        void shouldSetCorrectValue() {
            grid.setValue(ROW, COL, VALUE + 1);
            assertEquals(VALUE + 1, grid.getValue(ROW, COL));
        }

        @Test
        void shouldThrowWhenRowIsBelowMin() {
            assertThrows(IllegalArgumentException.class, () -> grid.setValue(BELOW_MIN_ROW, COL, VALUE));
        }

        @Test
        void shouldThrowWhenRowIsAboveMax() {
            assertThrows(IllegalArgumentException.class, () -> grid.setValue(ABOVE_MAX_ROW, COL, VALUE));
        }

        @Test
        void shouldThrowWhenColIsBelowMin() {
            assertThrows(IllegalArgumentException.class, () -> grid.setValue(ROW, BELOW_MIN_COL, VALUE));
        }

        @Test
        void shouldThrowWhenColIsAboveMax() {
            assertThrows(IllegalArgumentException.class, () -> grid.setValue(ROW, ABOVE_MAX_COL, VALUE));
        }

        @Test
        void shouldThrowWhenValueIsBelowMin() {
            assertThrows(IllegalArgumentException.class, () -> grid.setValue(ROW, COL, BELOW_MIN_VALUE));
        }

        @Test
        void shouldThrowWhenValueIsAboveMax() {
            assertThrows(IllegalArgumentException.class, () -> grid.setValue(ROW, COL, ABOVE_MAX_VALUE));
        }
    }

    @Nested
    class ClearValueTests {

        @BeforeEach
        void init() {
            grid = new Grid(createCells());
        }

        @Test
        void shouldClearCorrectValue() {
            grid.clearValue(ROW, COL);
            assertEquals(SudokuConstants.EMPTY_CELL, grid.getValue(ROW, COL));
        }

        @Test
        void shouldThrowWhenRowIsBelowMin() {
            assertThrows(IllegalArgumentException.class, () -> grid.clearValue(BELOW_MIN_ROW, COL));
        }

        @Test
        void shouldThrowWhenRowIsAboveMax() {
            assertThrows(IllegalArgumentException.class, () -> grid.clearValue(ABOVE_MAX_ROW, COL));
        }

        @Test
        void shouldThrowWhenColIsBelowMin() {
            assertThrows(IllegalArgumentException.class, () -> grid.clearValue(ROW, BELOW_MIN_COL));
        }

        @Test
        void shouldThrowWhenColIsAboveMax() {
            assertThrows(IllegalArgumentException.class, () -> grid.clearValue(ROW, ABOVE_MAX_COL));
        }
    }

    @Nested
    class IsLegalTests {

        @BeforeEach
        void init() {
            grid = new Grid(createCells());
        }

        @Test
        void shouldReturnTrueWhenPlacementIsLegal() {
            assertTrue(grid.isLegal(ROW, COL, VALUE + 1));
        }

        @Test
        void shouldReturnFalseWhenValueAlreadyExistsInRow() {
            int[][] cells = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
            cells[ROW][SudokuConstants.MAX_INDEX] = VALUE + 1;
            grid = new Grid(cells);
            assertFalse(grid.isLegal(ROW, COL, VALUE + 1));
        }

        @Test
        void shouldReturnFalseWhenValueAlreadyExistsInCol() {
            int[][] cells = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
            cells[SudokuConstants.MAX_INDEX][COL] = VALUE + 1;
            grid = new Grid(cells);
            assertFalse(grid.isLegal(ROW, COL, VALUE + 1));
        }

        @Test
        void shouldReturnFalseWhenValueAlreadyExistsInBox() {
            int[][] cells = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
            cells[SudokuConstants.BOX_SIZE - 1][SudokuConstants.BOX_SIZE - 1] = VALUE + 1;
            grid = new Grid(cells);
            assertFalse(grid.isLegal(ROW, COL, VALUE + 1));
        }

        @Test
        void shouldThrowWhenRowIsBelowMin() {
            assertThrows(IllegalArgumentException.class, () -> grid.isLegal(BELOW_MIN_ROW, COL, VALUE));
        }

        @Test
        void shouldThrowWhenRowIsAboveMax() {
            assertThrows(IllegalArgumentException.class, () -> grid.isLegal(ABOVE_MAX_ROW, COL, VALUE));
        }

        @Test
        void shouldThrowWhenColIsBelowMin() {
            assertThrows(IllegalArgumentException.class, () -> grid.isLegal(ROW, BELOW_MIN_COL, VALUE));
        }

        @Test
        void shouldThrowWhenColIsAboveMax() {
            assertThrows(IllegalArgumentException.class, () -> grid.isLegal(ROW, ABOVE_MAX_COL, VALUE));
        }

        @Test
        void shouldThrowWhenValueIsBelowMin() {
            assertThrows(IllegalArgumentException.class, () -> grid.isLegal(ROW, COL, BELOW_MIN_VALUE));
        }

        @Test
        void shouldThrowWhenValueIsAboveMax() {
            assertThrows(IllegalArgumentException.class, () -> grid.isLegal(ROW, COL, ABOVE_MAX_VALUE));
        }
    }

    @Nested
    class IsFullTests {

        @Test
        void shouldReturnTrueWhenGridContainsNoEmptyCells() {
            grid = new Grid(createCells());
            assertTrue(grid.isFull());
        }

        @Test
        void shouldReturnFalseWhenGridContainsAnEmptyCell() {
            int[][] cells = createCells();
            cells[8][8] = SudokuConstants.EMPTY_CELL;
            grid = new Grid(cells);
            assertFalse(grid.isFull());
        }

        @Test
        void shouldReturnFalseWhenGridIsEmpty() {
            grid = new Grid();
            assertFalse(grid.isFull());
        }
    }

    @Nested
    class IsValidTests {

        @Test
        void shouldReturnTrueWhenGridIsEmpty() {
            grid = new Grid();
            assertTrue(grid.isValid());
        }

        @Test
        void shouldReturnTrueWhenGridContainsNoDuplicates() {
            int[][] cells = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
            cells[0][0] = 1;
            cells[1][1] = 2;
            cells[2][2] = 3;
            cells[3][3] = 4;
            cells[4][4] = 5;
            cells[5][5] = 6;
            cells[6][6] = 7;
            cells[7][7] = 8;
            cells[8][8] = 9;
            grid = new Grid(cells);
            assertTrue(grid.isValid());
        }

        @Test
        void shouldReturnFalseWhenRowContainsDuplicates() {
            int[][] cells = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
            cells[0][0] = 1;
            cells[0][8] = 1;
            grid = new Grid(cells);
            assertFalse(grid.isValid());
        }

        @Test
        void shouldReturnFalseWhenColContainsDuplicates() {
            int[][] cells = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
            cells[0][0] = 1;
            cells[8][0] = 1;
            grid = new Grid(cells);
            assertFalse(grid.isValid());
        }

        @Test
        void shouldReturnFalseWhenBoxContainsDuplicates() {
            int[][] cells = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
            cells[0][0] = 1;
            cells[2][2] = 1;
            grid = new Grid(cells);
            assertFalse(grid.isValid());
        }
    }

    @Nested
    class CopyTests {

        @BeforeEach
        void init() {
            grid = new Grid(createCells());
        }

        @Test
        void shouldReturnGridCopy() {
            Grid copy = grid.copy();
            assertEquals(grid, copy);
        }

        @Test
        void shouldReturnIndependentGridCopy() {
            Grid copy = grid.copy();
            copy.setValue(ROW, COL, VALUE + 1);
            assertNotEquals(grid, copy);
        }
    }

    @Nested
    class EqualsTests {

        @BeforeEach
        void init() {
            grid = new Grid(createCells());
        }

        @Test
        void shouldReturnFalseWhenComparedToDifferentType() {
            Object object = new Object();
            assertNotEquals(grid, object);
        }
    }

    @Nested
    class HashCodeTests {

        @Test
        void shouldReturnSameHashCodesForEqualGrids() {
            grid = new Grid(createCells());
            Grid gridCopy = grid.copy();
            assertEquals(grid.hashCode(), gridCopy.hashCode());
        }
    }
}
