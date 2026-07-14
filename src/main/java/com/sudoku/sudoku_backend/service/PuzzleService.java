package com.sudoku.sudoku_backend.service;

import com.sudoku.sudoku_backend.core.Generator;
import com.sudoku.sudoku_backend.core.Grid;
import com.sudoku.sudoku_backend.core.Puzzle;
import com.sudoku.sudoku_backend.model.CellGrid;
import com.sudoku.sudoku_backend.model.PuzzleMapper;
import org.springframework.stereotype.Service;

@Service
public class PuzzleService {

    private final Generator generator;

    public PuzzleService(Generator generator) {
        this.generator = generator;
    }

    public CellGrid newPuzzle(Difficulty difficulty) {
        Grid grid = generator.generateGrid();
        Puzzle puzzle = generator.createPuzzle(grid, difficulty.getTarget());
        return PuzzleMapper.map(puzzle);
    }
}
