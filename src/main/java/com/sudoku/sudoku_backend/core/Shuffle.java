package com.sudoku.sudoku_backend.core;

import java.util.Random;

public class Shuffle {

    public static void fisherYatesShuffle(int[] array, Random random) {
        for (int i = array.length-1; i > 0; i--) {
            int j = random.nextInt(i+1);

            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    public static <T> void fisherYatesShuffle(T[] array, Random random) {
        for (int i = array.length-1; i > 0; i--) {
            int j = random.nextInt(i+1);

            T temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }
}
