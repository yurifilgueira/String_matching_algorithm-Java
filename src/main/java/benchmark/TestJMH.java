package benchmark;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import readers.Reader;
import util.DistanceCalculator;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@State(Scope.Benchmark)
public class TestJMH {

    private Stack<List<String>> blocks;

    @Setup(Level.Invocation)
    public void setup() throws IOException {
        Reader reader = new Reader();
        blocks = reader.getBlocks();
    }

    @Benchmark
    @BenchmarkMode({Mode.AverageTime, Mode.Throughput})
    @Warmup(iterations = 5, batchSize = 1)
    @Fork(value = 1)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void benchCalculateDistance(Blackhole bh) throws InterruptedException {

        List<Thread> threads = new ArrayList<>();

        AtomicInteger counter = new AtomicInteger();
        for (int i = 0; i < 6; i++) {
            Thread t = Thread.ofPlatform().name(String.valueOf(i)).unstarted(new DistanceCalculator(blocks.pop(), counter));
            threads.add(t);
            t.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        bh.consume(counter.get());
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