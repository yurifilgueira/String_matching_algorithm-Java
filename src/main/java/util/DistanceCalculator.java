package util;

import java.util.List;

public class DistanceCalculator {

    public static int calculateDistance(List<String> lines) {

        int counter = 0;

        for (String line : lines) {
            String[] words = line.split(" ");

            for (String word : words) {
                if (LevenshteinDistance.calculateDistance(word, "mouse") == 0){
                    counter += 1;
                }
            }

        }

        return counter;
    }
}
