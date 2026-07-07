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
    private static final int BELOW_MIN_ROW_INDEX = -1;
    private static final int ABOVE_MAX_ROW_INDEX = 9;
    private static final int COL_INDEX = 0;
    private static final int BELOW_MIN_COL_INDEX = -1;
    private static final int ABOVE_MAX_COL_INDEX = 9;
    private static final int VALUE = 1;
    private static final int BELOW_MIN_VALUE = -1;
    private static final int ABOVE_MAX_VALUE = 10;

    private static int[][] createValidCells() {
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
        void shouldInitialiseAllCellsToEmptyValue() {
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
            cells[ROW_INDEX][COL_INDEX] = BELOW_MIN_VALUE;
            assertThrows(IllegalArgumentException.class, () -> new Grid(cells));
        }

        @Test
        void shouldThrowWhenCellValueIsAboveUpperBound() {
            int[][] cells = createValidCells();
            cells[ROW_INDEX][COL_INDEX] = ABOVE_MAX_VALUE;
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
            assertThrows(IllegalArgumentException.class, () -> grid.getRow(BELOW_MIN_ROW_INDEX));
        }

        @Test
        void shouldThrowWhenRowIndexIsAboveUpperBound() {
            assertThrows(IllegalArgumentException.class, () -> grid.getRow(ABOVE_MAX_ROW_INDEX));
        }

        @Test
        void shouldReturnDefensiveCopy() {
            int[] copiedRow = grid.getRow(ROW_INDEX);
            copiedRow[COL_INDEX] = VALUE + 1;
            assertEquals(VALUE, grid.getRow(ROW_INDEX)[COL_INDEX]);
        }
    }

    @Nested
    class GetColTests {

        @BeforeEach
        void init() {
            grid = new Grid(createValidCells());
        }

        @Test
        void shouldReturnColOfCorrectLength() {
            for (int i = 0; i < SudokuConstants.GRID_SIZE; i++) {
                assertEquals(SudokuConstants.GRID_SIZE, grid.getCol(i).length);
            }
        }

        @Test
        void shouldThrowWhenColIndexIsBelowLowerBound() {
            assertThrows(IllegalArgumentException.class, () -> grid.getCol(BELOW_MIN_COL_INDEX));
        }

        @Test
        void shouldThrowWhenColIndexIsAboveUpperBound() {
            assertThrows(IllegalArgumentException.class, () -> grid.getCol(ABOVE_MAX_COL_INDEX));
        }

        @Test
        void shouldReturnDefensiveCopy() {
            int[] colCopy = grid.getCol(COL_INDEX);
            colCopy[ROW_INDEX] = VALUE + 1;
            assertEquals(VALUE, grid.getCol(COL_INDEX)[ROW_INDEX]);
        }
    }

    @Nested
    class GetBoxTests {

        @BeforeEach
        void init() {
            grid = new Grid(createValidCells());
        }

        @Test
        void shouldReturnBoxOfCorrectLength() {
            for (int i = 0; i < SudokuConstants.BOX_SIZE * SudokuConstants.BOX_SIZE; i++) {
                for (int j = 0; j < grid.getRow(i).length; j++) {
                    assertEquals(SudokuConstants.GRID_SIZE, grid.getBox(i, j).length);
                }
            }
        }

        @Test
        void shouldThrowWhenRowIndexIsBelowLowerBound() {
            assertThrows(IllegalArgumentException.class, () -> grid.getBox(BELOW_MIN_ROW_INDEX, COL_INDEX));
        }

        @Test
        void shouldThrowWhenRowIndexIsAboveUpperBound() {
            assertThrows(IllegalArgumentException.class, () -> grid.getBox(ABOVE_MAX_ROW_INDEX, COL_INDEX));
        }

        @Test
        void shouldThrowWhenColIndexIsBelowLowerBound() {
            assertThrows(IllegalArgumentException.class, () -> grid.getBox(ROW_INDEX, BELOW_MIN_COL_INDEX));
        }

        @Test
        void shouldThrowWhenColIndexIsAboveUpperBound() {
            assertThrows(IllegalArgumentException.class, () -> grid.getBox(ROW_INDEX, ABOVE_MAX_COL_INDEX));
        }

        @Test
        void shouldReturnBoxCellsInCorrectOrder() {
            int[][] cells = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
            cells[0][0] = 1; cells[0][1] = 2; cells[0][2] = 3;
            cells[1][0] = 4; cells[1][1] = 5; cells[1][2] = 6;
            cells[2][0] = 7; cells[2][1] = 8; cells[2][2] = 9;
            grid = new Grid(cells);
            assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, grid.getBox(ROW_INDEX, COL_INDEX));
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
            int[] boxCopy = grid.getBox(ROW_INDEX, COL_INDEX);
            boxCopy[ROW_INDEX] = VALUE + 1;
            assertEquals(VALUE, grid.getBox(ROW_INDEX, COL_INDEX)[ROW_INDEX]);
        }
    }

    @Nested
    class GetValueTests {

        @BeforeEach
        void init() {
            grid = new Grid(createValidCells());
        }

        @Test
        void shouldReturnCorrectValue() {
            assertEquals(VALUE, grid.getValue(ROW_INDEX, COL_INDEX));
        }

        @Test
        void shouldThrowWhenRowIndexIsBelowLowerBound() {
            assertThrows(IllegalArgumentException.class, () -> grid.getValue(BELOW_MIN_ROW_INDEX, COL_INDEX));
        }

        @Test
        void shouldThrowWhenRowIndexIsAboveUpperBound() {
            assertThrows(IllegalArgumentException.class, () -> grid.getValue(ABOVE_MAX_ROW_INDEX, COL_INDEX));
        }

        @Test
        void shouldThrowWhenColIndexIsBelowLowerBound() {
            assertThrows(IllegalArgumentException.class, () -> grid.getValue(ROW_INDEX, BELOW_MIN_COL_INDEX));
        }

        @Test
        void shouldThrowWhenColIndexIsAboveUpperBound() {
            assertThrows(IllegalArgumentException.class, () -> grid.getValue(ROW_INDEX, ABOVE_MAX_COL_INDEX));
        }
    }

    @Nested
    class SetValueTests {

        @BeforeEach
        void init() {
            grid = new Grid(createValidCells());
        }

        @Test
        void shouldSetCorrectCellValue() {
            grid.setValue(ROW_INDEX, COL_INDEX, VALUE + 1);
            assertEquals(VALUE + 1, grid.getValue(ROW_INDEX, COL_INDEX));
        }

        @Test
        void shouldThrowWhenRowIndexIsBelowLowerBound() {
            assertThrows(IllegalArgumentException.class, () -> grid.setValue(BELOW_MIN_ROW_INDEX, COL_INDEX, VALUE));
        }

        @Test
        void shouldThrowWhenRowIndexIsAboveUpperBound() {
            assertThrows(IllegalArgumentException.class, () -> grid.setValue(ABOVE_MAX_ROW_INDEX, COL_INDEX, VALUE));
        }

        @Test
        void shouldThrowWhenColIndexIsBelowLowerBound() {
            assertThrows(IllegalArgumentException.class, () -> grid.setValue(ROW_INDEX, BELOW_MIN_COL_INDEX, VALUE));
        }

        @Test
        void shouldThrowWhenColIndexIsAboveUpperBound() {
            assertThrows(IllegalArgumentException.class, () -> grid.setValue(ROW_INDEX, ABOVE_MAX_COL_INDEX, VALUE));
        }

        @Test
        void shouldThrowWhenCellValueIsBelowLowerBound() {
            assertThrows(IllegalArgumentException.class, () -> grid.setValue(ROW_INDEX, COL_INDEX, BELOW_MIN_VALUE));
        }

        @Test
        void shouldThrowWhenCellValueIsAboveUpperBound() {
            assertThrows(IllegalArgumentException.class, () -> grid.setValue(ROW_INDEX, COL_INDEX, ABOVE_MAX_VALUE));
        }
    }

    @Nested
    class ClearValueTests {

        @BeforeEach
        void init() {
            grid = new Grid(createValidCells());
        }

        @Test
        void shouldClearCorrectCellValue() {
            grid.clearValue(ROW_INDEX, COL_INDEX);
            assertEquals(SudokuConstants.EMPTY_CELL, grid.getValue(ROW_INDEX, COL_INDEX));
        }

        @Test
        void shouldThrowWhenRowIndexIsBelowLowerBound() {
            assertThrows(IllegalArgumentException.class, () -> grid.clearValue(BELOW_MIN_ROW_INDEX, COL_INDEX));
        }

        @Test
        void shouldThrowWhenRowIndexIsAboveUpperBound() {
            assertThrows(IllegalArgumentException.class, () -> grid.clearValue(ABOVE_MAX_ROW_INDEX, COL_INDEX));
        }

        @Test
        void shouldThrowWhenColIndexIsBelowLowerBound() {
            assertThrows(IllegalArgumentException.class, () -> grid.clearValue(ROW_INDEX, BELOW_MIN_COL_INDEX));
        }

        @Test
        void shouldThrowWhenColIndexIsAboveUpperBound() {
            assertThrows(IllegalArgumentException.class, () -> grid.clearValue(ROW_INDEX, ABOVE_MAX_COL_INDEX));
        }
    }

    @Nested
    class IsLegalTests {

        @BeforeEach
        void init() {
            grid = new Grid(createValidCells());
        }

        @Test
        void shouldReturnTrueWhenPlacementIsLegal() {
            assertTrue(grid.isLegal(ROW_INDEX, COL_INDEX, VALUE + 1));
        }

        @Test
        void shouldReturnFalseWhenCellValueExistsInRow() {
            int[][] cells = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
            cells[ROW_INDEX][SudokuConstants.MAX_INDEX] = VALUE + 1;
            grid = new Grid(cells);
            assertFalse(grid.isLegal(ROW_INDEX, COL_INDEX, VALUE + 1));
        }

        @Test
        void shouldReturnFalseWhenCellValueExistsInCol() {
            int[][] cells = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
            cells[SudokuConstants.MAX_INDEX][COL_INDEX] = VALUE + 1;
            grid = new Grid(cells);
            assertFalse(grid.isLegal(ROW_INDEX, COL_INDEX, VALUE + 1));
        }

        @Test
        void shouldReturnFalseWhenCellValueExistsInBox() {
            int[][] cells = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
            cells[SudokuConstants.BOX_SIZE - 1][SudokuConstants.BOX_SIZE - 1] = VALUE + 1;
            grid = new Grid(cells);
            assertFalse(grid.isLegal(ROW_INDEX, COL_INDEX, VALUE + 1));
        }

        @Test
        void shouldThrowWhenRowIndexIsBelowLowerBound() {
            assertThrows(IllegalArgumentException.class, () -> grid.isLegal(BELOW_MIN_ROW_INDEX, COL_INDEX, VALUE));
        }

        @Test
        void shouldThrowWhenRowIndexIsAboveUpperBound() {
            assertThrows(IllegalArgumentException.class, () -> grid.isLegal(ABOVE_MAX_ROW_INDEX, COL_INDEX, VALUE));
        }

        @Test
        void shouldThrowWhenColIndexIsBelowLowerBound() {
            assertThrows(IllegalArgumentException.class, () -> grid.isLegal(ROW_INDEX, BELOW_MIN_COL_INDEX, VALUE));
        }

        @Test
        void shouldThrowWhenColIndexIsAboveUpperBound() {
            assertThrows(IllegalArgumentException.class, () -> grid.isLegal(ROW_INDEX, ABOVE_MAX_COL_INDEX, VALUE));
        }

        @Test
        void shouldThrowWhenCellValueIsBelowLowerBound() {
            assertThrows(IllegalArgumentException.class, () -> grid.isLegal(ROW_INDEX, COL_INDEX, BELOW_MIN_VALUE));
        }

        @Test
        void shouldThrowWhenCellValueIsAboveUpperBound() {
            assertThrows(IllegalArgumentException.class, () -> grid.isLegal(ROW_INDEX, COL_INDEX, ABOVE_MAX_VALUE));
        }
    }

    @Nested
    class IsFullTests {

        @Test
        void shouldReturnTrueWhenGridContainsNoEmptyCells() {
            grid = new Grid(createValidCells());
            assertTrue(grid.isFull());
        }

        @Test
        void shouldReturnFalseWhenGridContainsAnEmptyCell() {
            int[][] cells = createValidCells();
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
            grid = new Grid(createValidCells());
        }

        @Test
        void shouldReturnCopyOfGrid() {
            Grid gridCopy = grid.copy();
            assertEquals(grid, gridCopy);
        }

        @Test
        void shouldReturnIndependentCopyOfGrid() {
            Grid gridCopy = grid.copy();
            gridCopy.setValue(ROW_INDEX, COL_INDEX, VALUE + 1);
            assertNotEquals(grid, gridCopy);
        }
    }

    @Nested
    class EqualsTests {

        @BeforeEach
        void init() {
            grid = new Grid(createValidCells());
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
            grid = new Grid(createValidCells());
            Grid gridCopy = grid.copy();
            assertEquals(grid.hashCode(), gridCopy.hashCode());
        }
    }
}
