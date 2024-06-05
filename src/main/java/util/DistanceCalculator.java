package util;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class DistanceCalculator implements Runnable{

    private final List<String> lines;
    private final AtomicInteger counter;

    public DistanceCalculator(List<String> lines, AtomicInteger counter) {
        this.lines = lines;
        this.counter = counter;
    }

    public void calculateDistance() {

        System.out.println(Thread.currentThread().getName() + " -> started.");

        for (String line : lines) {
            String[] words = line.split(" ");

            for (String word : words) {
                if (LevenshteinDistance.calculateDistance(word, "mouse") == 0) {
                    counter.incrementAndGet();
                }
            }
        }

        System.out.println(Thread.currentThread().getName() + " -> finished.");
    }

    @Override
    public void run() {
        calculateDistance();
    }
}
