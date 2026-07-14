package com.sudoku.sudoku_backend.service;

import com.sudoku.sudoku_backend.core.Generator;
import org.springframework.stereotype.Service;

@Service
public class PuzzleService {

    private final Generator generator;

    public PuzzleService(Generator generator) {
        this.generator = generator;
    }
}
