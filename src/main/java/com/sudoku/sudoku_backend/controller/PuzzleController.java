package com.sudoku.sudoku_backend.controller;

import com.sudoku.sudoku_backend.service.Difficulty;
import com.sudoku.sudoku_backend.service.GuessResult;
import com.sudoku.sudoku_backend.service.NewPuzzle;
import com.sudoku.sudoku_backend.service.PuzzleService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/puzzles")
public class PuzzleController {

    private final PuzzleService puzzleService;

    public PuzzleController(PuzzleService puzzleService) {
        this.puzzleService = puzzleService;
    }

    // POST /api/puzzles -> newPuzzle -> NewPuzzle -> CellGridMapper -> CellResponse -> PuzzleResponse
    @PostMapping
    public PuzzleResponse createPuzzle(@RequestParam Difficulty difficulty) {
        NewPuzzle newPuzzle = puzzleService.newPuzzle(difficulty);
        return PuzzleResponse.from(newPuzzle);
    }

    // POST /api/puzzles/{id}/guesses -> makeGuess -> GuessResult -> GuessResponse
    @PostMapping("/{id}/guesses")
    public GuessResponse makeGuess(@PathVariable long id, int row, int col, int value) {
        GuessResult guessResult = puzzleService.makeGuess(id, row, col, value);
        return GuessResponse.from(guessResult);
    }

    // GET /api/puzzles/{id}/solved -> calls isSolved
    @GetMapping("/{id}/solved")
    public boolean isSolved(@PathVariable long id) {
        return puzzleService.isSolved(id);
    }
}
