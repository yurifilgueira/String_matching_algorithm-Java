package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.nio.file.Files.newBufferedReader;

public class DatasetReader {

    private String PATH;
    private static List<String> lines;

    public DatasetReader() {
    }

    public DatasetReader(String PATH) {
        this.PATH = PATH;
    }

    public static void readDatasetAndProcess(String PATH, List<String> items) {

        Map<String, Integer> matches = new HashMap<>();

        try (BufferedReader br = newBufferedReader(Paths.get(PATH))) {

            int counter = 0;
            String line;
            while ((line = br.readLine()) != null) {
                counter++;
                String[] arrayRatingLine = line.split(",");
                String rating = arrayRatingLine[2].replaceAll("\"", "").toLowerCase();
                String[] words = rating.split(" ");

                for (String item : items) {
                    for (String word : words) {
                        if (LevenshteinDistance.calculateDistance(item, word) == 0){
                            matches.put(word, matches.getOrDefault(word, 0) + 1);
                            System.out.println("Match: " + item + " - " + word);
                            break;
                        }
                    }
                }
            }

            System.out.println("Count: " + counter);
            matches.forEach((k, v) -> System.out.println("Match: " + k + " - " + v));

            ResultSaver.save(matches);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
