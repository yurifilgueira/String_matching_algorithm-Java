import util.DatasetReader;
import util.DistanceCalculator;
import util.ResultSaver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException {

        List<Thread> threads = new ArrayList<>();

        Map<String, Integer> matches = new HashMap<>();

        var blocks = DatasetReader.getBlocks();

        System.out.println("Starting threads...");

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 6; i++) {
            Thread t = Thread.ofPlatform().name(String.valueOf(i)).unstarted(new DistanceCalculator(blocks.pop(), matches));
            threads.add(t);
            t.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("Total read and print time: " + (double) (System.currentTimeMillis() - startTime) / 60000);

        ResultSaver.save(matches);
    }
}
