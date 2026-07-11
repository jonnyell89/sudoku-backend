package com.sudoku.sudoku_backend.model;

import com.sudoku.sudoku_backend.SudokuConstants;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static com.sudoku.sudoku_backend.SudokuTestConstants.*;

public class CellTest {

    @Nested
    class EmptyCellFactoryTests {

        @Test
        void shouldInstantiateEmptyCell() {
            Cell cell = Cell.empty(ROW, COL);
            assertTrue(cell.isEmpty());
            assertTrue(cell.isEditable());
            assertFalse(cell.isGiven());
            assertEquals(ROW, cell.getRow());
            assertEquals(COL, cell.getCol());
            assertEquals(SudokuConstants.EMPTY_CELL, cell.getValue());
        }

        @Test
        void shouldThrowWhenRowIsBelowMin() {
            assertThrows(IllegalArgumentException.class, () -> Cell.empty(ROW_BELOW_MIN, COL));
        }

        @Test
        void shouldThrowWhenRowIsAboveMax() {
            assertThrows(IllegalArgumentException.class, () -> Cell.empty(ROW_ABOVE_MAX, COL));
        }

        @Test
        void shouldThrowWhenColIsBelowMin() {
            assertThrows(IllegalArgumentException.class, () -> Cell.empty(ROW, COL_BELOW_MIN));
        }

        @Test
        void shouldThrowWhenColIsAboveMax() {
            assertThrows(IllegalArgumentException.class, () -> Cell.empty(ROW, COL_ABOVE_MAX));
        }
    }

    @Nested
    class GivenCellFactoryTests {

        @Test
        void shouldInstantiateGivenCell() {
            Cell cell = Cell.given(ROW, COL, VALUE);
            assertTrue(cell.isGiven());
            assertFalse(cell.isEmpty());
            assertFalse(cell.isEditable());
            assertEquals(ROW, cell.getRow());
            assertEquals(COL, cell.getCol());
            assertEquals(VALUE, cell.getValue());
        }

        @Test
        void shouldThrowWhenRowIsBelowMin() {
            assertThrows(IllegalArgumentException.class, () -> Cell.given(ROW_BELOW_MIN, COL, VALUE));
        }

        @Test
        void shouldThrowWhenRowIsAboveMax() {
            assertThrows(IllegalArgumentException.class, () -> Cell.given(ROW_ABOVE_MAX, COL, VALUE));
        }

        @Test
        void shouldThrowWhenColIsBelowMin() {
            assertThrows(IllegalArgumentException.class, () -> Cell.given(ROW, COL_BELOW_MIN, VALUE));
        }

        @Test
        void shouldThrowWhenColIsAboveMax() {
            assertThrows(IllegalArgumentException.class, () -> Cell.given(ROW, COL_ABOVE_MAX, VALUE));
        }

        @Test
        void shouldThrowWhenValueIsBelowMin() {
            assertThrows(IllegalArgumentException.class, () -> Cell.given(ROW, COL, VALUE_BELOW_MIN));
        }

        @Test
        void shouldThrowWhenValueIsAboveMax() {
            assertThrows(IllegalArgumentException.class, () -> Cell.given(ROW, COL, VALUE_ABOVE_MAX));
        }
    }

    @Nested
    class SetValueTests {

        @Test
        void shouldSetValueOnEditableCell() {
            Cell cell = Cell.empty(ROW, COL);
            cell.setValue(VALUE);
            assertEquals(VALUE, cell.getValue());
        }

        @Test
        void shouldThrowWhenCellIsGiven() {
            Cell cell = Cell.given(ROW, COL, VALUE);
            assertThrows(IllegalStateException.class, () -> cell.setValue(VALUE));
        }

        @Test
        void shouldThrowWhenValueIsBelowMin() {
            Cell cell = Cell.empty(ROW, COL);
            assertThrows(IllegalArgumentException.class, () -> cell.setValue(VALUE_BELOW_MIN));
        }

        @Test
        void shouldThrowWhenValueIsAboveMax() {
            Cell cell = Cell.empty(ROW, COL);
            assertThrows(IllegalArgumentException.class, () -> cell.setValue(VALUE_ABOVE_MAX));
        }

        @Test
        void shouldThrowIllegalStateExceptionBeforeIllegalArgumentExceptionOnGivenCell() {
            Cell cell = Cell.given(ROW, COL, VALUE);
            assertThrows(IllegalStateException.class, () -> cell.setValue(VALUE_BELOW_MIN));
        }
    }

    @Nested
    class ClearValueTests {

        @Test
        void shouldClearValueOnEditableCell() {
            Cell cell = Cell.empty(ROW, COL);
            cell.setValue(VALUE);
            cell.clearValue();
            assertEquals(SudokuConstants.EMPTY_CELL, cell.getValue());
        }

        @Test
        void shouldThrowWhenCellIsGiven() {
            Cell cell = Cell.given(ROW, COL, VALUE);
            assertThrows(IllegalStateException.class, cell::clearValue);
        }
    }

    @Nested
    class IsGivenTests {

        @Test
        void shouldReturnFalseForEmptyCell() {
            Cell cell = Cell.empty(ROW, COL);
            assertFalse(cell.isGiven());
        }

        @Test
        void shouldReturnTrueForGivenCell() {
            Cell cell = Cell.given(ROW, COL, VALUE);
            assertTrue(cell.isGiven());
        }
    }

    @Nested
    class IsEditableTests {

        @Test
        void shouldReturnTrueForEmptyCell() {
            Cell cell = Cell.empty(ROW, COL);
            assertTrue(cell.isEditable());
        }

        @Test
        void shouldReturnFalseForGivenCell() {
            Cell cell = Cell.given(ROW, COL, VALUE);
            assertFalse(cell.isEditable());
        }
    }

    @Nested
    class IsEmptyTests {

        @Test
        void shouldReturnTrueForEmptyCell() {
            Cell cell = Cell.empty(ROW, COL);
            assertTrue(cell.isEmpty());
        }

        @Test
        void shouldReturnFalseForGivenCell() {
            Cell cell = Cell.given(ROW, COL, VALUE);
            assertFalse(cell.isEmpty());
        }

        @Test
        void shouldReturnTrueAfterClearingEditableCellToEmptyValue() {
            Cell cell = Cell.empty(ROW, COL);
            cell.setValue(VALUE);
            cell.clearValue();
            assertTrue(cell.isEmpty());
        }
    }
}
