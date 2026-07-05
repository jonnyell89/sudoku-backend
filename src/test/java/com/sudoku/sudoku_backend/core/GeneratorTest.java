package com.sudoku.sudoku_backend.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;

import java.util.Random;

public class GeneratorTest {

    private final Random random = new Random();

    private Generator generator;

    @BeforeEach
    void init() {
        generator = new Generator(random);
    }

    @Nested
    class GenerateGridTests {

    }

    @Nested
    class CreatePuzzleTests {

    }
}
