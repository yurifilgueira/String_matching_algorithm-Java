package bench.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import readers.Reader;
import util.DistanceCalculator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class Bench {

    private Stack<List<String>> blocks;

    @Setup(value = Level.Invocation)
    public void setup() throws IOException {
        blocks = Reader.getBlocks();
    }


    @Benchmark
    @BenchmarkMode({Mode.AverageTime, Mode.Throughput})
    @Warmup(iterations = 5)
    @Fork(value = 1)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void benchCalculateDistance(Blackhole bh) throws InterruptedException {

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

            bh.consume(totalCount);
        }

    }

    @Benchmark
    @BenchmarkMode({Mode.AverageTime, Mode.Throughput})
    @Warmup(iterations = 5)
    @Fork(value = 1)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void benchReadFile(Blackhole bh) throws IOException {

        final List<String> lines = Reader.readFile();

        bh.consume(lines);
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