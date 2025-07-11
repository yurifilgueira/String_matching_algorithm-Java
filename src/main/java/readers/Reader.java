package readers;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Files.newBufferedReader;

public class Reader {

    private final static String PATH = "resources/train.csv";

    public Reader() {
    }
// Test
    public static List<String> readFile() throws IOException {

        try (BufferedReader br = newBufferedReader(Path.of(PATH))){
            List<String> lines = new ArrayList<>();
            String line;

            while ((line = br.readLine()) != null) {
                String[] arrayRatingLine = line.split(",");
                String rating = arrayRatingLine[2].replaceAll("\"", "").toLowerCase();
                lines.add(rating);
            }

            return lines;
        }
    }
}
