package util;

import java.util.Map;
import java.util.concurrent.Semaphore;

public class MatchingComputer {

    private static final Semaphore semaphore = new Semaphore(1);

    public static void compute(String word, Map<String, Integer> matches) {
        try {
            semaphore.acquire();
            matches.put(word, matches.getOrDefault(word, 0) + 1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            semaphore.release();
        }

    }
}
