import util.DatasetReader;
import util.DistanceCalculator;
import util.ResultSaver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException {

        final List<Thread> threads = new ArrayList<>();
        final var blocks = DatasetReader.getBlocks();

        System.out.println("Starting threads...");

        long startTime = System.currentTimeMillis();

        final AtomicInteger counter = new AtomicInteger(0);
        for (int i = 0; i < 6; i++) {
            Thread t = Thread.ofPlatform().name("Calculate distance: " + i).unstarted(new DistanceCalculator(blocks.pop(), counter));
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
