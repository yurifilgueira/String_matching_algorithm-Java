import readers.Reader;
import util.DistanceCalculator;

import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException {

        long startTime = System.currentTimeMillis();

        List<Thread> threads = new ArrayList<>();

        Reader reader = new Reader();
        Stack<List<String>> blocks = reader.getBlocks();

        System.out.println("Starting threads...");

        for (int i = 0; i < 6; i++) {
            Thread t = Thread.ofPlatform().name(String.valueOf(i)).start(new DistanceCalculator(blocks.pop()));
            threads.add(t);
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("Total read and print time: " + (double) (System.currentTimeMillis() - startTime) / 1000);
    }
}
