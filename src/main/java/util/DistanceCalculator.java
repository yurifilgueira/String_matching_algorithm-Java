package util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DistanceCalculator {

    public static void calculateDistance(List<String> lines) {

        Map<String, Integer> matches = new HashMap<>();

        for (String line : lines) {
            String[] arrayRatingLine = line.split(",");
            String rating = arrayRatingLine[2].replaceAll("\"", "").toLowerCase();
            String[] words = rating.split(" ");

            for (String word : words) {
                if (LevenshteinDistance.calculateDistance(word, "mouse") == 0){
                    matches.put(word, matches.getOrDefault(word, 0) + 1);
                }
            }

        }

        ResultSaver.save(matches);

    }

}
