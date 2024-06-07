package util;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class DistanceCalculator {

    private List<String> lines;

    public DistanceCalculator() {
    }

    public DistanceCalculator(List<String> lines) {
        this.lines = lines;
    }

    public Integer calculateDistance() {

        final AtomicInteger count = new AtomicInteger(0);
        System.out.println(Thread.currentThread().getName() + " -> started.");

        for (String line : lines) {
            String[] words = line.split(" ");

            for (String word : words) {
                if (LevenshteinDistance.calculateDistance(word, "mouse") == 0) {
                    count.incrementAndGet();
                }
            }
        }

        System.out.println(Thread.currentThread().getName() + " -> finished.");

        return count.get();
    }

}