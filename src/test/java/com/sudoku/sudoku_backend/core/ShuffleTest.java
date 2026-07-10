package com.sudoku.sudoku_backend.core;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

public class ShuffleTest {

    private static final int SEED = 24;
    private static final int SEED_COUNT = 24;

    static IntStream seeds() {
        return IntStream.range(0, SEED_COUNT);
    }

    @Nested
    class FisherYatesIntArrayTests {

        @Test
        void shouldNotMutateEmptyArray() {
            Random random = new Random();
            int[] array = new int[0];
            int[] copy = Arrays.copyOf(array, array.length);
            Shuffle.fisherYates(array, random);
            assertArrayEquals(copy, array);
        }

        @Test
        void shouldNotMutateSingleElementArray() {
            Random random = new Random();
            int[] array = new int[]{1};
            int[] copy = Arrays.copyOf(array, array.length);
            Shuffle.fisherYates(array, random);
            assertArrayEquals(copy, array);
        }

        @ParameterizedTest
        @MethodSource("com.sudoku.sudoku_backend.core.ShuffleTest#seeds")
        void shouldShuffleArrayInPlace(int seed) {
            Random seededRandom = new Random(seed);
            int[] array = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
            int[] copy = Arrays.copyOf(array, array.length);
            Shuffle.fisherYates(array, seededRandom);
            assertFalse(Arrays.equals(copy, array));
        }

        @ParameterizedTest
        @MethodSource("com.sudoku.sudoku_backend.core.ShuffleTest#seeds")
        void shouldMaintainArrayElements(int seed) {
            Random seededRandom = new Random(seed);
            int[] array = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
            Shuffle.fisherYates(array, seededRandom);
            Set<Integer> seen = new HashSet<>();
            for (int element : array) {
                seen.add(element);
            }
            assertEquals(seen.size(), array.length);
        }

        @Test
        void shouldCreateIdenticalShuffleWhenUsingIdenticalSeeds() {
            Random expectedRandom = new Random(SEED);
            Random actualRandom = new Random(SEED);
            int[] expectedArray = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
            int[] actualArray = Arrays.copyOf(expectedArray, expectedArray.length);
            Shuffle.fisherYates(expectedArray, expectedRandom);
            Shuffle.fisherYates(actualArray, actualRandom);
            assertArrayEquals(expectedArray, actualArray);
        }
    }
}
