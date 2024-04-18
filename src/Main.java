import util.DatasetReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        final String PATH = "resources\\train.csv";

        long startTime = System.currentTimeMillis();

        AtomicInteger count = new AtomicInteger();
        Map<String, Integer> matches = new HashMap<>();

        List<String> items = new ArrayList<>(List.of("logitech", "keyboard", "mouse", "hyperx", "razer", "lenovo", "acer", "lg", "samsung", "laptop"));

        DatasetReader.readDatasetAndProcess(PATH, items);

        System.out.println("Total read and print time: " + (double) (System.currentTimeMillis() - startTime) / 60000);
        // System.out.println("Count: " + count);
    }
}
