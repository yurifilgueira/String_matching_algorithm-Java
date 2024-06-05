package util;

import java.util.List;
import java.util.concurrent.atomic.LongAdder;

public class DistanceCalculator implements Runnable{

    private List<String> lines;
    private LongAdder counter;

    public DistanceCalculator() {
    }

    public DistanceCalculator(List<String> lines, LongAdder counter) {
        this.lines = lines;
        this.counter = counter;
    }

    public void calculateDistance() {

        System.out.println(Thread.currentThread().getName() + " -> started.");

        for (String line : lines) {
            String[] words = line.split(" ");

            for (String word : words) {
                if (LevenshteinDistance.calculateDistance(word, "mouse") == 0) {
                    counter.increment();
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
