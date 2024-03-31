package util;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class CalculateDistance implements Runnable{

    private List<String> lines;
    private AtomicInteger count;
    private String item;
    private Map<String, Integer> matches;

    public CalculateDistance(List<String> lines, AtomicInteger count, String item, Map<String, Integer> matches) {
        this.lines = lines;
        this.count = count;
        this.item = item;
        this.matches = matches;
    }

    @Override
    public void run() {
        lines.forEach(line -> {
            String[] arrayRatingLine = line.split(",");
            String rating = arrayRatingLine[2].replaceAll("\"", "").toLowerCase();
            String[] words = rating.split(" ");

            count.getAndIncrement();
            for (String word : words) {
                if (LevenshteinDistance.calculateDistance(item, word) == 0) {
                    matches.put(item, matches.getOrDefault(item, 0) + 1);
                    break;
                }
            }
        });
    }


}
