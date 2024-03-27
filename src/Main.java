import util.LevenshteinDistance;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    private static final String PATH = "resources\\train.csv";

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        int count = 0;
        Map<String, Integer> matches = new HashMap<>();

        try (BufferedReader bfRating = new BufferedReader(new FileReader(PATH))) {

            String ratingLine;

            List<String> items = new ArrayList<>(List.of("logitech", "mouse", "keyboard", "hyperx", "razer"));

            while ((ratingLine = bfRating.readLine()) != null) {
                count++;

                for (String item : items) {
                    String[] arrayRatingLine = ratingLine.split(",");
                    String rating = arrayRatingLine[2].replaceAll("\"", "").toLowerCase();
                    String[] words = rating.split(" ");

                    for (String s : words) {
                        int distance = LevenshteinDistance.calculateDistance(s, item);
                        if (distance == 0) {

                            matches.put(s, matches.getOrDefault(s, 0) + 1);
                            System.out.println(item);
                            break;
                        }
                    }
                }
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Total read and print time: " + (double) (System.currentTimeMillis() - startTime) / 60000);
        System.out.println("Count: " + count);
        matches.forEach((k, v) -> {
            System.out.println(k + "-> " + v);
        });
    }
}
