import util.DatasetReader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        final String PATH = "resources\\train.csv";

        long startTime = System.currentTimeMillis();

        List<String> items = new ArrayList<>(List.of("logitech", "keyboard", "mouse", "hyperx", "razer", "lenovo", "acer", "lg", "samsung", "laptop"));

        List<Thread> threads = new ArrayList<>();

        ConcurrentHashMap matches = new ConcurrentHashMap();

        for (int i = 0; i < items.size(); i++) {
            Thread t = Thread.ofVirtual().name(String.valueOf(i)).start(new DatasetReader(items.get(i), items.get(++i), matches));
            threads.add(t);
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("Total read and print time: " + (double) (System.currentTimeMillis() - startTime) / 60000);
        // System.out.println("Count: " + count);
        matches.forEach((k, v) -> System.out.println("Match: " + k + " - " + v));
    }
}
