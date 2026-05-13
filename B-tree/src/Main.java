import java.util.Random;

public class Main {

    public static void main(String[] args) {

        int[] data = RandomArrayGenerator.generate(10000);

        BTree tree = new BTree(3);

        BenchmarkResult addResult = new BenchmarkResult();
        BenchmarkResult searchResult = new BenchmarkResult();
        BenchmarkResult removeResult = new BenchmarkResult();

        for (int value : data) {

            tree.addOperations = 0;

            long start = System.nanoTime();

            tree.add(value);

            long end = System.nanoTime();

            addResult.add(
                    end - start,
                    tree.addOperations
            );
        }

        Random random = new Random();

        for (int i = 0; i < 100; i++) {

            int value = data[random.nextInt(data.length)];

            tree.searchOperations = 0;

            long start = System.nanoTime();

            tree.contains(value);

            long end = System.nanoTime();

            searchResult.add(
                    end - start,
                    tree.searchOperations
            );
        }

        for (int i = 0; i < 1000; i++) {

            int value = data[random.nextInt(data.length)];

            tree.removeOperations = 0;

            long start = System.nanoTime();

            tree.remove(value);

            long end = System.nanoTime();

            removeResult.add(
                    end - start,
                    tree.removeOperations
            );
        }
        System.out.println("ДОБАВЛЕНИЕ");

        System.out.println("Среднее время: " +
                addResult.getAverageTime() + " ns");

        System.out.println("Среднее число операций: " +
                addResult.getAverageOperations());

        System.out.println();

        System.out.println("ПОИСК");

        System.out.println("Среднее время: " +
                searchResult.getAverageTime() + " ns");

        System.out.println("Среднее число операций: " +
                searchResult.getAverageOperations());

        System.out.println();

        System.out.println("УДАЛЕНИЕ");

        System.out.println("Среднее время: " +
                removeResult.getAverageTime() + " ns");

        System.out.println("Среднее число операций: " +
                removeResult.getAverageOperations());
    }
}