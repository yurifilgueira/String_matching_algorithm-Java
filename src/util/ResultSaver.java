package util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.concurrent.atomic.LongAdder;

import static java.nio.file.Files.newBufferedWriter;

public class ResultSaver {

    private final static String PATH = "resources\\results.txt";

    public static void save(LongAdder counter){

        try (BufferedWriter bw = newBufferedWriter(Paths.get(PATH))) {

            bw.write("Quantity of matches:");
            bw.newLine();
            bw.write(counter.toString());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
