package util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import static util.MatchingComputer.compute;

public class DistanceCalculator implements Runnable{

    private List<String> lines;
    private Map<String, Integer> matches;
    private ReentrantLock mutex = new ReentrantLock();
    public DistanceCalculator() {
    }

    public DistanceCalculator(List<String> lines, Map<String, Integer> matches) {
        this.lines = lines;
        this.matches = matches;
    }

    public void calculateDistance() {

        List<String> items = new ArrayList<>(List.of("logitech", "keyboard", "mouse", "hyperx", "razer", "lenovo", "acer", "lg", "samsung", "laptop", "headset", "jbl"));

        System.out.println(Thread.currentThread().getName() + " -> started.");

        for (String line : lines) {
            String[] arrayRatingLine = line.split(",");
            String rating = arrayRatingLine[2].replaceAll("\"", "").toLowerCase();
            String[] words = rating.split(" ");

            for (String item : items) {

                for (String word : words) {
                    if (LevenshteinDistance.calculateDistance(word, item) == 0) {
                        compute(word, matches);
                        break;
                    }
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