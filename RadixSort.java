package org.example;

public class RadixSort {

    public static long iterations = 0;

    public static void radixSort(int[] arr) {
        iterations = 0;

        if (arr == null || arr.length <= 1) return;

        int max = getMax(arr);

        for (int place = 1; max / place > 0; place *= 10) {
            countingSort(arr, place);
        }
    }

    private static int getMax(int[] arr) {
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            iterations++;
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        return max;
    }

    private static void countingSort(int[] arr, int place) {
        int n = arr.length;

        int[] output = new int[n];
        int[] count = new int[10];

        for (int i = 0; i < n; i++) {
            iterations++;
            int digit = (arr[i] / place) % 10;
            count[digit]++;
        }

        for (int i = 1; i < 10; i++) {
            iterations++;
            count[i] += count[i - 1];
        }

        for (int i = n - 1; i >= 0; i--) {
            iterations++;
            int digit = (arr[i] / place) % 10;
            output[--count[digit]] = arr[i];
        }

        System.arraycopy(output, 0, arr, 0, n);
    }
}


