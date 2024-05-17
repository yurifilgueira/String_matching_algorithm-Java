package util;

import java.util.List;
import java.util.concurrent.RecursiveTask;

public class DistanceCalculator extends RecursiveTask<Integer> {

    private static final int THRESHOLD = 100;

    private final List<String> lines;
    private final int start;
    private final int end;

    public DistanceCalculator(List<String> lines) {
        this(lines, 0, lines.size());
    }

    private DistanceCalculator(List<String> lines, int start, int end) {
        this.lines = lines;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        if (end - start <= THRESHOLD) {
            return calculateDistance();
        } else {
            int mid = start + (end - start) / 2;
            DistanceCalculator left = new DistanceCalculator(lines, start, mid);
            DistanceCalculator right = new DistanceCalculator(lines, mid, end);
            left.fork();
            int rightResult = right.compute();
            int leftResult = left.join();
            return leftResult + rightResult;
        }
    }

    private Integer calculateDistance() {
        System.out.println(Thread.currentThread().getName() + " -> started.");

        int count = 0;
        for (int i = start; i < end; i++) {
            String line = lines.get(i);
            String[] arrayRatingLine = line.split(",");
            String rating = arrayRatingLine[2].replaceAll("\"", "").toLowerCase();
            String[] words = rating.split(" ");

            for (String word : words) {
                if (LevenshteinDistance.calculateDistance("mouse", word) == 0) {
                    count += 1;
                }
            }

        }

        System.out.println(Thread.currentThread().getName() + " -> finished.");
        return count;
    }

}
