
package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DistanceCalculator implements Runnable{

    private List<String> lines;
    private Integer start;
    private Integer end;

    public DistanceCalculator() {
    }

    public DistanceCalculator(List<String> lines, Integer start, Integer end) {
        this.lines = lines;
        this.start = start;
        this.end = end;
    }

    public void calculateDistance() {

        List<String> items = new ArrayList<>(List.of("logitech", "keyboard", "mouse", "hyperx", "razer", "lenovo", "acer", "lg", "samsung", "laptop", "headset", "jbl"));
        Map<String, Integer> matches = new HashMap<>();

        System.out.println(Thread.currentThread().getName() + " -> started.");

        for (int i = start; i < end; i++) {
            String[] arrayRatingLine = lines.get(i).split(",");
            String rating = arrayRatingLine[2].replaceAll("\"", "").toLowerCase();
            String[] words = rating.split(" ");

            for (String item : items) {

                for (String word : words) {
                    if (LevenshteinDistance.calculateDistance(word, item) == 0){
                        matches.put(word, matches.getOrDefault(word, 0) + 1);
                        break;
                    }
                }
            }
        }

        System.out.println(Thread.currentThread().getName() + " -> finished.");
        ResultSaver.save(matches);

    }

    @Override
    public void run() {
        calculateDistance();
    }
}