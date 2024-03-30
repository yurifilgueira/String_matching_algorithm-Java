import util.DatasetReader;
import util.LevenshteinDistance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static void main(String[] args) {

        String PATH = "resources\\train.csv";

        long startTime = System.currentTimeMillis();

        AtomicInteger count = new AtomicInteger();
        Map<String, Integer> matches = new HashMap<>();

        List<String> lines = DatasetReader.readFile(PATH);

        List<String> items = new ArrayList<>(List.of("logitech", "keyboard", "mouse", "hyperx", "razer"));

        if (lines != null) {

            lines.forEach(line -> {
                String[] arrayRatingLine = line.split(",");
                String rating = arrayRatingLine[2].replaceAll("\"", "").toLowerCase();
                String[] words = rating.split(" ");

                count.getAndIncrement();
                items.forEach(item -> {
                    for (String word : words) {
                        if (LevenshteinDistance.calculateDistance(item, word) == 0) {
                            matches.put(item, matches.getOrDefault(item, 0) + 1);
                            break;
                        }
                    }
                });
            });
        }
        else {
            throw new NullPointerException("Line list is null!");
        }

        System.out.println("Total read and print time: " + (double) (System.currentTimeMillis() - startTime) / 60000);
        System.out.println("Count: " + count);
        matches.forEach((k, v) -> {
            System.out.println(k + "-> " + v);
        });
    }
}
