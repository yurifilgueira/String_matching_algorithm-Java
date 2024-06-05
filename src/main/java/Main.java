import readers.Reader;
import util.DistanceCalculator;
import util.ResultSaver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException {

        List<Thread> threads = new ArrayList<>();

        long startTime = System.currentTimeMillis();
        Reader reader = new Reader();
        var blocks = reader.getBlocks();

        System.out.println("Starting threads...");

        List<Future<Integer>> futures = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            FutureTask<Integer> futureTask = new FutureTask<>(new DistanceCalculator(blocks.pop()));
            futures.add(futureTask);
            Thread t = Thread.ofVirtual().name("Distance calculator " + i).unstarted(futureTask);
            threads.add(t);
            t.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        final int count = futures.stream().mapToInt(future -> {
            try {
                return future.get();
            }
            catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }).sum();

        System.out.println("Total read and print time: " + (double) (System.currentTimeMillis() - startTime) / 1000);

        ResultSaver.save(count);
    }
}
