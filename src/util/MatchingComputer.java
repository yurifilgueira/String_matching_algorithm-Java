package util;

import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class MatchingComputer {

    private static final ReentrantLock mutex = new ReentrantLock();

    public static void compute(Map<String, Integer> counter, String item) {
        mutex.lock();
        counter.put(item, counter.getOrDefault(item, 0) + 1);
        mutex.unlock();
    }
}
