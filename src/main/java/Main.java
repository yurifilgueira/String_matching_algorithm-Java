import readers.Reader;
import util.DistanceCalculator;
import util.ResultSaver;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException, ExecutionException {

        long startTime = System.currentTimeMillis();
        Reader reader = new Reader();
        var blocks = reader.getBlocks();
        System.out.println("Starting threads...");

        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();

        int count = forkJoinPool.submit(() -> blocks.stream()
                .mapToInt(block -> forkJoinPool.invoke(new DistanceCalculator(block)))
                .sum()).get();

        System.out.println("Total read and print time: " + (double) (System.currentTimeMillis() - startTime) / 1000);

        ResultSaver.save(count);

        forkJoinPool.shutdown();
    }
}
