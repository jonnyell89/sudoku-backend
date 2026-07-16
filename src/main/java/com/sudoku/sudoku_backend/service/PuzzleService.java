package com.sudoku.sudoku_backend.service;

import com.sudoku.sudoku_backend.core.Generator;
import com.sudoku.sudoku_backend.core.Grid;
import com.sudoku.sudoku_backend.core.Puzzle;
import com.sudoku.sudoku_backend.model.CellGrid;
import com.sudoku.sudoku_backend.model.PuzzleMapper;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PuzzleService {

    private final Generator generator;

    public PuzzleService(Generator generator) {
        Objects.requireNonNull(generator, "generator must not be null.");
        this.generator = generator;
    }

    public CellGrid newPuzzle(Difficulty difficulty) {
        Objects.requireNonNull(difficulty, "difficulty must not be null.");
        Grid grid = generator.generateGrid();
        Puzzle puzzle = generator.createPuzzle(grid, difficulty.getTarget());
        return PuzzleMapper.map(puzzle);
    }
}
