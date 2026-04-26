package org.example;

import java.io.*;
import java.util.*;

public class Benchmark {

    public static void main(String[] args) throws IOException {

        File dir = new File("data");
        File[] files = dir.listFiles((d, n) -> n.endsWith(".txt"));

        if (files == null || files.length == 0) {
            System.out.println("No files found");
            return;
        }

        Arrays.sort(files);

        try (FileWriter small = new FileWriter("results_small.csv");
             FileWriter big = new FileWriter("results_big.csv")) {

            small.write("size,time_ns,iterations\n");
            big.write("size,time_ns,iterations\n");

            for (File file : files) {

                int[] arr = readArray(file);

                long start = System.nanoTime();
                RadixSort.radixSort(arr);
                long end = System.nanoTime();

                long time = end - start;
                long iter = RadixSort.iterations;

                String line = arr.length + "," + time + "," + iter + "\n";

                if (file.getName().startsWith("small")) {
                    small.write(line);
                } else {
                    big.write(line);
                }

                System.out.println("Processed: " + file.getName());
            }
        }

        System.out.println("Benchmark done");
    }

    private static int[] readArray(File file) throws IOException {

        List<Integer> list = new ArrayList<>();

        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextInt()) {
                list.add(sc.nextInt());
            }
        }

        return list.stream().mapToInt(i -> i).toArray();
    }
}