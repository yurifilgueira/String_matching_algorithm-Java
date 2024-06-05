package util;

import java.util.List;
import java.util.concurrent.Callable;

public class DistanceCalculator implements Callable<Integer> {

    private List<String> lines;

    public DistanceCalculator() {
    }

    public DistanceCalculator(List<String> lines) {
        this.lines = lines;
    }

    public Integer calculateDistance() {

        System.out.println(Thread.currentThread().getName() + " -> started.");

        int count = 0;
        for (String line : lines) {
            String[] words = line.split(" ");

            for (String word : words) {
                if (LevenshteinDistance.calculateDistance(word, "mouse") == 0) {
                    count += 1;
                }
            }
        }

        System.out.println(Thread.currentThread().getName() + " -> finished.");
        return count;
    }

    @Override
    public Integer call() {
        return calculateDistance();
    }
}
