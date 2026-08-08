package com.sudoku.sudoku_backend.controller;

import com.sudoku.sudoku_backend.service.*;
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
    public GuessResponse makeGuess(@PathVariable long id, @RequestBody GuessRequest guessRequest) {
        GuessResult guessResult = puzzleService.makeGuess(id, guessRequest.row(), guessRequest.col(), guessRequest.value());
        return GuessResponse.from(guessResult);
    }

    // GET /api/puzzles/{id}/solved -> isSolved -> SolvedResult -> SolvedResponse
    @GetMapping("/{id}/solved")
    public SolvedResponse isSolved(@PathVariable long id) {
        SolvedResult solvedResult = puzzleService.isSolved(id);
        return SolvedResponse.from(solvedResult);
    }
}
