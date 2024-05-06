import util.DatasetReader;
import util.DistanceCalculator;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        List<String> lines = DatasetReader.readFile();

        long startTime = System.currentTimeMillis();

        DistanceCalculator.calculateDistance(lines);

        System.out.println("Total read and print time: " + (double) (System.currentTimeMillis() - startTime) / 10000);
    }
}
