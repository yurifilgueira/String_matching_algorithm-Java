package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Stack;

public class DatasetReader {

    private static final String PATH = "resources\\train.csv";

    public DatasetReader() {
    }

    public static List<String> readFile() throws IOException {
        return List.copyOf(Files.readAllLines(Paths.get(PATH)));
    }

    public static Stack<List<String>> getBlocks() throws IOException {

        List<String> file = readFile();

        Stack<List<String>> stack = new Stack<>();
        for (int i = 0; i < 3600000; i += 600000) {
            stack.push(List.copyOf(file.subList(i, i + 600000)));
        }

        return stack;
    }
}
