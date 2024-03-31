import util.CalculateDistance;
import util.DatasetReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static void main(String[] args) {

        String PATH = "resources\\train.csv";

        long startTime = System.currentTimeMillis();

        AtomicInteger count = new AtomicInteger();
        Map<String, Integer> matches = new HashMap<>();

        List<String> lines = DatasetReader.readFile(PATH);

        List<String> items = new ArrayList<>(List.of("logitech", "keyboard", "mouse", "hyperx", "razer"));

        if (lines != null) {

            List<Thread> threads = new ArrayList<>();

            int idx = 0;
            for (String item : items) {
                Thread thread = Thread.ofPlatform().name(item).start(new CalculateDistance(lines, count, item, matches));
                threads.add(thread);
            }

            for (Thread thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            throw new NullPointerException("Line list is null!");
        }

        System.out.println("Total read and print time: " + (double) (System.currentTimeMillis() - startTime) / 60000);
        System.out.println("Count: " + count);
        matches.forEach((k, v) -> {
            System.out.println(k + "-> " + v);
        });

    }
}
