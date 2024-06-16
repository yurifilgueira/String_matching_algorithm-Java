import readers.Reader;
import util.DistanceCalculator;
import util.ResultSaver;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        long startTime = System.currentTimeMillis();
        List<String> lines = Reader.readFile();

        int result = DistanceCalculator.calculateDistance(lines);

        ResultSaver.save(result);

        System.out.println("Total read and print time: " + (double) (System.currentTimeMillis() - startTime) / 1000);
    }
}
