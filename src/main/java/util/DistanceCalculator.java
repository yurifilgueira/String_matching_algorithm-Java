package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DistanceCalculator {

    public static void calculateDistance(List<String> lines) {

        List<String> items = new ArrayList<>(List.of("logitech", "keyboard", "mouse", "hyperx", "razer", "lenovo", "acer", "lg", "samsung", "laptop"));
        Map<String, Integer> matches = new HashMap<>();

        for (String line : lines) {
            String[] arrayRatingLine = line.split(",");
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

        ResultSaver.save(matches);

    }

}
