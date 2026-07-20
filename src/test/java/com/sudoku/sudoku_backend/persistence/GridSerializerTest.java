package com.sudoku.sudoku_backend.persistence;

import com.sudoku.sudoku_backend.SudokuConstants;
import com.sudoku.sudoku_backend.core.Grid;
import org.junit.jupiter.api.Test;

import static com.sudoku.sudoku_backend.core.TestGrids.*;
import static org.junit.jupiter.api.Assertions.*;

public class GridSerializerTest {

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
        assertEquals(expected, GridSerializer.serialize(new Grid(data)));
    }

    @Test
    void shouldReturnGridAs81CharacterString() {
        assertEquals(SudokuConstants.GRID_CELLS, GridSerializer.serialize(new Grid(easy)).length());
    }

    @Test
    void shouldReturnEmptyGridAs81ZeroCharacters() {
        Grid grid = new Grid();
        String expected = "0".repeat(SudokuConstants.GRID_CELLS);
        assertEquals(expected, GridSerializer.serialize(grid));
    }

    @Test
    void shouldThrowWhenGridIsNull() {
        assertThrows(NullPointerException.class, () -> GridSerializer.serialize(null));
    }

    @Test
    void shouldThrowWhenGridIsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> GridSerializer.serialize(new Grid(duplicates)));
    }
}
