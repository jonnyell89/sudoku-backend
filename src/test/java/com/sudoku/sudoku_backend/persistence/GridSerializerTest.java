package com.sudoku.sudoku_backend.persistence;

import com.sudoku.sudoku_backend.SudokuConstants;
import com.sudoku.sudoku_backend.core.Grid;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.sudoku.sudoku_backend.persistence.GridSerializer.*;
import static com.sudoku.sudoku_backend.core.TestGrids.*;
import static org.junit.jupiter.api.Assertions.*;

public class GridSerializerTest {

    @Nested
    class SerializeTests {

        @Test
        void shouldSerializeKnownGridToExpectedString() {
            int[][] data = {
                    {1, 2, 3, 4, 5, 6, 7, 8, 9},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 3},
            };
            String expected = "123456789" + "0".repeat(71) + "3";
            assertEquals(expected, serialize(new Grid(data)));
        }

        @Test
        void shouldReturnGridWithCorrectLength() {
            assertEquals(SudokuConstants.GRID_CELLS, serialize(new Grid(easy)).length());
        }

        @Test
        void shouldReturnEmptyGridAsZeroes() {
            String expected = "0".repeat(SudokuConstants.GRID_CELLS);
            assertEquals(expected, serialize(new Grid()));
        }

        @Test
        void shouldThrowWhenGridIsNull() {
            assertThrows(NullPointerException.class, () -> serialize(null));
        }

        @Test
        void shouldThrowWhenGridIsInvalid() {
            assertThrows(IllegalArgumentException.class, () -> serialize(new Grid(duplicates)));
        }
    }

    @Nested
    class DeserializeTests {

        @Test
        void shouldRoundTrip() {
            Grid grid = new Grid(easy);
            assertEquals(grid, deserialize(serialize(grid)));
        }

        @Test
        void shouldDeserializeKnownStringToExpectedGrid() {
            int[][] data = {
                    {1, 2, 3, 4, 5, 6, 7, 8, 9},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 3},
            };
            String serialized = "123456789" + "0".repeat(71) + "3";
            assertEquals(new Grid(data), deserialize(serialized));
        }

        @Test
        void shouldThrowWhenSerializedGridIsNull() {
            assertThrows(NullPointerException.class, () -> deserialize(null));
        }

        @Test
        void shouldThrowWhenSerializedGridHasTooFewCharacters() {
            assertThrows(IllegalArgumentException.class, () -> deserialize(""));
        }

        @Test
        void shouldThrowWhenSerializedGridHasTooManyCharacters() {
            assertThrows(IllegalArgumentException.class, () -> deserialize("0".repeat(SudokuConstants.GRID_CELLS + 1)));
        }

        @Test
        void shouldThrowWhenSerializedGridIsInvalid() {
            assertThrows(IllegalArgumentException.class, () -> deserialize(serialize(new Grid(duplicates))));
        }
    }
}
