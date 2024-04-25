package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class DatasetReader {

    public static final String PATH = "resources\\train.csv";

    public DatasetReader() {
    }

    public static List<String> readFile() throws IOException {
        return Files.readAllLines(Paths.get(PATH));
    }
}
