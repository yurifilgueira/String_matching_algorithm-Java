package benchmark;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import util.DatasetReader;
import util.DistanceCalculator;

import java.io.IOException;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

@State(Scope.Benchmark)
public class BenchTest {

    public Stack<List<String>> getBlocks() throws IOException {
        return DatasetReader.getBlocks();
    }

    @Benchmark
    public void test() throws IOException {
        Stack<List<String>> blocks = getBlocks();

        AtomicInteger counter = new AtomicInteger(0);
        for (int i = 0; i < 6; i++) {
            Thread t = Thread.ofVirtual().name(String.valueOf(i)).unstarted(new DistanceCalculator(blocks.pop(), counter));
            t.start();
        }
    }
}
