package com.sudoku.sudoku_backend.model;

import com.sudoku.sudoku_backend.SudokuConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static com.sudoku.sudoku_backend.SudokuTestConstants.*;

public class CellGridTest {

    private CellGrid cellGrid;

    private Cell[][] createCells() {
        Cell[][] cells = new Cell[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
        for (int row = 0; row < SudokuConstants.GRID_SIZE; row++) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; col++) {
                cells[row][col] = Cell.empty(row, col);
            }
        }
        return cells;
    }

    @Nested
    class ConstructorTests {

        @Test
        void shouldAcceptCells() {
            assertDoesNotThrow(() -> new CellGrid(createCells()));
        }

        @Test
        void shouldThrowWhenCellsIsNull() {
            assertThrows(NullPointerException.class, () -> new CellGrid(null));
        }

        @Test
        void shouldThrowWhenCellsHasTooFewRows() {
            Cell[][] cells = new Cell[SudokuConstants.GRID_SIZE - 1][SudokuConstants.GRID_SIZE];
            assertThrows(IllegalArgumentException.class, () -> new CellGrid(cells));
        }

        @Test
        void shouldThrowWhenCellsHasTooManyRows() {
            Cell[][] cells = new Cell[SudokuConstants.GRID_SIZE + 1][SudokuConstants.GRID_SIZE];
            assertThrows(IllegalArgumentException.class, () -> new CellGrid(cells));
        }

        @Test
        void shouldThrowWhenRowIsNull() {
            Cell[][] cells = createCells();
            cells[ROW] = null;
            assertThrows(NullPointerException.class, () -> new CellGrid(cells));
        }

        @Test
        void shouldThrowWhenRowHasTooFewCells() {
            Cell[][] cells = createCells();
            cells[ROW] = new Cell[SudokuConstants.GRID_SIZE - 1];
            assertThrows(IllegalArgumentException.class, () -> new CellGrid(cells));
        }

        @Test
        void shouldThrowWhenRowHasTooManyCells() {
            Cell[][] cells = createCells();
            cells[ROW] = new Cell[SudokuConstants.GRID_SIZE + 1];
            assertThrows(IllegalArgumentException.class, () -> new CellGrid(cells));
        }

        @Test
        void shouldThrowWhenCellIsNull() {
            Cell[][] cells = createCells();
            cells[ROW][COL] = null;
            assertThrows(NullPointerException.class, () -> new CellGrid(cells));
        }
    }

    @Nested
    class GetCellTests {

        @BeforeEach
        void init() {
            cellGrid = new CellGrid(createCells());
        }

        @Test
        void shouldContainCells() {
            for (int row = 0; row < SudokuConstants.GRID_SIZE; row++) {
                for (int col = 0; col < SudokuConstants.GRID_SIZE; col++) {
                    Cell cell = cellGrid.getCell(row, col);
                    assertEquals(Cell.class, cell.getClass());
                }
            }
        }

        @Test
        void shouldContainCorrectValues() {
            for (int row = 0; row < SudokuConstants.GRID_SIZE; row++) {
                for (int col = 0; col < SudokuConstants.GRID_SIZE; col++) {
                    Cell cell = cellGrid.getCell(row, col);
                    assertEquals(SudokuConstants.EMPTY_CELL, cell.getValue());
                }
            }
        }

        @Test
        void shouldThrowWhenRowIsBelowMin() {
            assertThrows(IllegalArgumentException.class, () -> cellGrid.getCell(ROW_BELOW_MIN, COL));
        }

        @Test
        void shouldThrowWhenRowIsAboveMax() {
            assertThrows(IllegalArgumentException.class, () -> cellGrid.getCell(ROW_ABOVE_MAX, COL));
        }

        @Test
        void shouldThrowWhenColIsBelowMin() {
            assertThrows(IllegalArgumentException.class, () -> cellGrid.getCell(ROW, COL_BELOW_MIN));
        }

        @Test
        void shouldThrowWhenColIsAboveMax() {
            assertThrows(IllegalArgumentException.class, () -> cellGrid.getCell(ROW, COL_ABOVE_MAX));
        }
    }
}
