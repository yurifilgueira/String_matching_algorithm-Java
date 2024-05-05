package util;

import java.util.List;
import java.util.Map;

import static util.MatchingComputer.compute;

public class DistanceCalculator implements Runnable{

    private List<String> lines;
    private Map<String, Integer> counter;

    public DistanceCalculator() {
    }

    public DistanceCalculator(List<String> lines, Map<String, Integer> counter) {
        this.lines = lines;
        this.counter = counter;
    }

    public void calculateDistance() {

        System.out.println(Thread.currentThread().getName() + " -> started.");

        for (String line : lines) {
            String[] arrayRatingLine = line.split(",");
            String rating = arrayRatingLine[2].replaceAll("\"", "").toLowerCase();
            String[] words = rating.split(" ");

            for (String word : words) {
                if (LevenshteinDistance.calculateDistance(word, "mouse") == 0) {
                    compute(counter, word);
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