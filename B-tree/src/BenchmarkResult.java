public class BenchmarkResult {

    long totalTime;
    long totalOperations;

    int count;

    public void add(long time, long operations) {

        totalTime += time;
        totalOperations += operations;

        count++;
    }

    public double getAverageTime() {
        return (double) totalTime / count;
    }

    public double getAverageOperations() {
        return (double) totalOperations / count;
    }
}