package readers;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static java.nio.file.Files.newBufferedReader;

public class Reader {

    private final static String PATH = "resources/train.csv";

    public Reader() {
    }

    public static List<String> readFile() throws IOException {

        try (BufferedReader br = newBufferedReader(Path.of(PATH))){
            final List<String> lines = new ArrayList<>();
            String line;

            while ((line = br.readLine()) != null) {
                String[] arrayRatingLine = line.split(",");
                String rating = arrayRatingLine[2].replaceAll("\"", "").toLowerCase();
                lines.add(rating);
            }

            return lines;
        }
    }


    public Stack<List<String>> getBlocks() throws IOException {
        List<String> file = readFile();

        Stack<List<String>> stack = new Stack<>();
        for (int i = 0; i < 3600000; i += 600000) {
            stack.push(List.copyOf(file.subList(i, i + 600000)));
        }

        return stack;
    }
}
