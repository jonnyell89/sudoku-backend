package com.sudoku.sudoku_backend.controller;

import com.sudoku.sudoku_backend.SudokuConstants;
import com.sudoku.sudoku_backend.model.Cell;
import com.sudoku.sudoku_backend.model.CellGrid;
import com.sudoku.sudoku_backend.service.Difficulty;
import com.sudoku.sudoku_backend.service.NewPuzzle;
import com.sudoku.sudoku_backend.service.PuzzleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static com.sudoku.sudoku_backend.SudokuTestConstants.*;

@WebMvcTest(PuzzleController.class)
public class PuzzleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PuzzleService puzzleService;

    private static final int GIVEN_ROW = 0;
    private static final int GIVEN_COL = 0;
    private static final int GIVEN_VALUE = 1;

    private static CellGrid knownCellGrid() {
        Cell[][] cells = new Cell[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
        for (int row = 0; row < SudokuConstants.GRID_SIZE; row++) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; col++) {
                cells[row][col] = Cell.empty(row, col);
            }
        }
        cells[GIVEN_ROW][GIVEN_COL] = Cell.given(GIVEN_ROW, GIVEN_COL, GIVEN_VALUE);
        return new CellGrid(cells);
    }

    @Test
    void shouldReturnPuzzleWhenCreated() throws Exception {
        NewPuzzle newPuzzle = new NewPuzzle(ID, knownCellGrid());
        when(puzzleService.newPuzzle(Difficulty.EASY))
                .thenReturn(newPuzzle);

        mockMvc.perform(post("/api/puzzles").param("difficulty", "EASY"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.cells[0][0].value").value(VALUE))
                .andExpect(jsonPath("$.cells[0][0].given").value(true))
                .andExpect(jsonPath("$.cells[0][1].value").value(SudokuConstants.EMPTY_CELL))
                .andExpect(jsonPath("$.cells[0][1].given").value(false));
    }

    @Test
    void shouldReturn404WhenGameNotFound() throws Exception {
        when(puzzleService.makeGuess(INVALID_ID, ROW, COL, VALUE))
                .thenThrow(new java.util.NoSuchElementException(String.format("Puzzle Not Found with id: %d", INVALID_ID)));

        mockMvc.perform(post(String.format("/api/puzzles/%d/guesses", INVALID_ID))
                        .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                        .content(String.format("{\"row\":%d,\"col\":%d,\"value\":%d}", ROW, COL, VALUE)))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.title").value("Puzzle Not Found"))
                .andExpect(jsonPath("$.detail").value(String.format("Puzzle Not Found with id: %d", INVALID_ID)));
    }

    @Test
    void shouldReturn400WhenServiceRejectsGuess() throws Exception {
        when(puzzleService.makeGuess(ID, ROW_ABOVE_MAX, COL, VALUE))
                .thenThrow(new IllegalArgumentException(String.format("row must be between %d and %d", MIN_INDEX, MAX_INDEX)));

        mockMvc.perform(post(String.format("/api/puzzles/%d/guesses", ID))
                        .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                        .content(String.format("{\"row\":%d,\"col\":%d,\"value\":%d}", ROW_ABOVE_MAX, COL, VALUE)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.title").value("Invalid Guess"))
                .andExpect(jsonPath("$.detail").value(String.format("row must be between %d and %d", MIN_INDEX, MAX_INDEX)));
    }
}
