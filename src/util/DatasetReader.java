package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class DatasetReader {

    public static List<String> readFile(String src) {
        Path path = Paths.get(src);
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}
