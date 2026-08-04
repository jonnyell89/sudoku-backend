package com.sudoku.sudoku_backend.controller;

import com.sudoku.sudoku_backend.persistence.NewGame;
import com.sudoku.sudoku_backend.service.Difficulty;
import com.sudoku.sudoku_backend.service.PuzzleService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/puzzles")
public class PuzzleController {

    private final PuzzleService puzzleService;

    public PuzzleController(PuzzleService puzzleService) {
        this.puzzleService = puzzleService;
    }

    // POST /api/puzzles -> calls newPuzzle
    @PostMapping
    public PuzzleResponse createGame(@RequestParam Difficulty difficulty) {
        NewGame newGame = puzzleService.newPuzzle(difficulty);
        return PuzzleResponse.from(newGame);
    }

    // POST /api/puzzles/{id}/guesses -> calls checkGuess
    @PostMapping("/{id}/guesses")
    public boolean makeGuess(@PathVariable long id, int row, int col, int value) {
        return puzzleService.checkGuess(id, row, col, value);
    }

    // GET /api/puzzles/{id}/solved -> calls isSolved
    @GetMapping("/{id}/solved")
    public boolean checkState(@PathVariable long id) {
        return puzzleService.isSolved(id);
    }
}
