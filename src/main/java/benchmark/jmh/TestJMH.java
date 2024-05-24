package benchmark.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import util.DatasetReader;
import util.DistanceCalculator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@State(Scope.Benchmark)
public class TestJMH {

    private Stack<List<String>> blocks;

    @Setup(Level.Trial)
    public void setup() throws IOException {
        blocks = DatasetReader.getBlocks();
    }

    @Benchmark
    @BenchmarkMode({Mode.AverageTime, Mode.Throughput})
    @Warmup(iterations = 5, batchSize = 1)
    @Fork(value = 1)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void benchCalculateDistance(Blackhole bh) throws InterruptedException {

        List<Thread> threads = new ArrayList<>();

        AtomicInteger counter = new AtomicInteger(0);
        for (int i = 0; i < 6; i++) {
            Thread t = Thread.ofVirtual().name("Distance calculator " + i).unstarted(new DistanceCalculator(blocks.get(i), counter));
            threads.add(t);
            t.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        bh.consume(counter.get());
    }

}