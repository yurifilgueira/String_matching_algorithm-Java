import util.DatasetReader;
import util.DistanceCalculator;
import util.ResultSaver;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        long startTime = System.currentTimeMillis();
        List<String> lines = DatasetReader.readFile();

        int result = DistanceCalculator.calculateDistance(lines);

        ResultSaver.save(result);

        System.out.println("Total read and print time: " + (double) (System.currentTimeMillis() - startTime) / 1000);
    }
}
