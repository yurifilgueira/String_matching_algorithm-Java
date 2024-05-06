package util;

import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class MatchingComputer {

    private static final ReentrantLock mutex = new ReentrantLock();

    public static void compute(String word, Map<String, Integer> counter) {
        mutex.lock();
        counter.put(word, counter.getOrDefault(word, 0) + 1);
        mutex.unlock();
    }
}
