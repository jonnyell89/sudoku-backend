package com.sudoku.sudoku_backend.config;

import com.sudoku.sudoku_backend.core.Generator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class GeneratorConfig {

    @Bean
    public Random random() {
        return new Random();
    }

    @Bean
    public Generator generator(Random random) {
        return new Generator(random);
    }
}
