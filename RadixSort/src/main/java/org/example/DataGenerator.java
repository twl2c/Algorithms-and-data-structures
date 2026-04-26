package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class DataGenerator {

    private static final int FILES = 50;

    private static final int MIN_SIZE = 100;
    private static final int MAX_SIZE = 10_000;

    public static void main(String[] args) throws IOException {

        File dir = new File("data");
        if (!dir.exists()) dir.mkdir();

        generate(false);
        generate(true);

        System.out.println("Done");
    }

    private static void generate(boolean big) throws IOException {

        Random rnd = new Random();

        for (int i = 0; i < FILES; i++) {

            int size = MIN_SIZE + i * (MAX_SIZE - MIN_SIZE) / (FILES - 1);

            int[] arr = new int[size];

            for (int j = 0; j < size; j++) {
                if (big) {
                    arr[j] = rnd.nextInt(Integer.MAX_VALUE);
                } else {
                    arr[j] = rnd.nextInt(10_000);
                }
            }

            String name = (big ? "big_" : "small_") + size + ".txt";
            File file = new File("data/" + name);

            try (FileWriter w = new FileWriter(file)) {
                for (int x : arr) {
                    w.write(x + " ");
                }
            }

            System.out.println("Generated: " + name);
        }
    }
}