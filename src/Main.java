import readers.Reader;
import util.DistanceCalculator;
import util.ResultSaver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException {

        long startTime = System.currentTimeMillis();

        List<Thread> threads = new ArrayList<>();

        ConcurrentHashMap<String, Integer> counter = new ConcurrentHashMap<>();

        Reader reader = new Reader();
        var blocks = reader.getBlocks();

        System.out.println("Starting threads...");

        for (int i = 0; i < 6; i++) {
            Thread t = Thread.ofVirtual().name(String.valueOf(i)).unstarted(new DistanceCalculator(counter, blocks.pop()));
            threads.add(t);
            t.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("Total read and print time: " + (double) (System.currentTimeMillis() - startTime) / 1000);

        ResultSaver.save(counter);
    }
}
