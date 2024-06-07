package util;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class DistanceCalculator implements Runnable{

    private List<String> lines;
    private final ConcurrentHashMap<String, Integer> counter;
    
    public DistanceCalculator() {
        counter = new ConcurrentHashMap<>();
    }

    public DistanceCalculator(ConcurrentHashMap<String, Integer> counter, List<String> lines) {
        this.counter = counter;
        this.lines = lines;
    }

    public void calculateDistance() {

        System.out.println(Thread.currentThread().getName() + " -> started.");

        for (String line : lines) {
            String[] words = line.split(" ");

            for (String word : words) {
                if (LevenshteinDistance.calculateDistance(word, "mouse") == 0) {
                    counter.put(word, counter.getOrDefault(word, 0) + 1);
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
