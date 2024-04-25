import util.DatasetReader;
import util.DistanceCalculator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException {

        List<Thread> threads = new ArrayList<>();

        Map<String, Integer> matches = new HashMap<>();

        List<String> lines = DatasetReader.readFile();

        System.out.println("Starting threads...");

        long startTime = System.currentTimeMillis();

        int start = 0;
        int end = 600000;
        for (int i = 0; i < 6; i++) {
            Thread t = Thread.ofVirtual().name(String.valueOf(i)).start(new DistanceCalculator(lines, start, end, matches));
            start += 600000;
            end += 600000;
            threads.add(t);
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("Total read and print time: " + (double) (System.currentTimeMillis() - startTime) / 60000);
        // System.out.println("Count: " + count);
        // matches.forEach((k, v) -> System.out.println("Match: " + k + " - " + v));
    }
}