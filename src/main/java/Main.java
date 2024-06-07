import readers.Reader;
import util.DistanceCalculator;
import util.ResultSaver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.StructuredTaskScope;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException {

        long startTime = System.currentTimeMillis();

        List<Thread> threads = new ArrayList<>();

        ConcurrentHashMap<String, Integer> counter = new ConcurrentHashMap<>();

        var blocks = Reader.getBlocks();

        System.out.println("Starting threads...");

        try (var taskScope = new StructuredTaskScope.ShutdownOnFailure()) {

            List<StructuredTaskScope.Subtask<Integer>> tasks = new ArrayList<>();
            for (var block : blocks) {
                DistanceCalculator calculator = new DistanceCalculator(block);
                var future = taskScope.fork(calculator::calculateDistance);

                tasks.add(future);

            }
            taskScope.join();

            int totalCount = tasks.stream()
                    .map(task -> getFutureResult(task))
                    .mapToInt(Integer::intValue)
                    .sum();

            System.out.println(totalCount);
        }

        System.out.println(STR."Total read and print time: \{(double) (System.currentTimeMillis() - startTime) / 1000}");

        ResultSaver.save(counter);
    }

    private static Integer getFutureResult(StructuredTaskScope.Subtask<Integer> future) {
        try {
            return future.get();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
