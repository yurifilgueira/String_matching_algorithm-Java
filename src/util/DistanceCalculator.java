package util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DistanceCalculator implements Runnable{

    private List<String> lines;
    private Integer start;
    private Integer end;
    private Map<String, Integer> matches;

    public DistanceCalculator() {
    }

    public DistanceCalculator(List<String> lines, Integer start, Integer end, Map<String, Integer> matches) {
        this.lines = lines;
        this.start = start;
        this.end = end;
        this.matches = matches;
    }

    public void calculateDistance() {

        List<String> items = new ArrayList<>(List.of("logitech", "keyboard", "mouse", "hyperx", "razer", "lenovo", "acer", "lg", "samsung", "laptop", "headset", "jbl"));

        System.out.println(Thread.currentThread().getName() + " -> started.");

        for (int i = start; i < end; i++) {
            String[] arrayRatingLine = lines.get(i).split(",");
            String rating = arrayRatingLine[2].replaceAll("\"", "").toLowerCase();
            String[] words = rating.split(" ");

            for (String item : items) {

                for (String word : words) {
                    if (LevenshteinDistance.calculateDistance(word, item) == 0){
                        compute(word);
                        break;
                    }
                }
            }
        }

        System.out.println(Thread.currentThread().getName() + " -> finished.");
        ResultSaver.save(matches);

    }

    public void compute(String word) {
        matches.put(word, matches.getOrDefault(word, 0) + 1);
    }

    @Override
    public void run() {
        calculateDistance();
    }
}