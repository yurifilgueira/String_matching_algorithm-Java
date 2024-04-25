package util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;

import static java.nio.file.Files.newBufferedWriter;

public class ResultSaver {

    private final static String PATH = "resources\\results.txt";

    public static void save(Map<String, Integer> matches){

        try (BufferedWriter bw = newBufferedWriter(Paths.get(PATH))) {

            bw.write("Quantity of matches:");
            bw.newLine();
            matches.forEach((k, v) -> {

                try {
                    bw.write(k + " -> " + v);
                    bw.newLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
