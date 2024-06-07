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

        final List<Thread> threads = new ArrayList<>();
        final ConcurrentHashMap<String, Integer> counter = new ConcurrentHashMap<>();

        for (int i = 0; i < 6; i++) {
            Thread t = Thread.ofVirtual().name(String.valueOf(i)).unstarted(new DistanceCalculator(counter, blocks.pop()));
            threads.add(t);
            t.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        bh.consume(counter.values().stream().findFirst());
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

}