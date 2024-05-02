import util.DatasetReader;
import util.DistanceCalculator;
import util.ResultSaver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.LongAdder;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException {

        List<Thread> threads = new ArrayList<>();

        var blocks = DatasetReader.getBlocks();

        System.out.println("Starting threads...");

        long startTime = System.currentTimeMillis();

        LongAdder counter = new LongAdder();
        for (int i = 0; i < 6; i++) {
            Thread t = Thread.ofVirtual().name(String.valueOf(i)).unstarted(new DistanceCalculator(blocks.pop(), counter));
            threads.add(t);
            t.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("Total read and print time: " + (double) (System.currentTimeMillis() - startTime) / 60000);

        ResultSaver.save(counter);
    }
}
