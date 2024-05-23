package benchmark;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import util.DatasetReader;
import util.DistanceCalculator;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class TestJMH {

    private List<String> lines;

    @Setup
    public void setup() throws IOException {
        lines = DatasetReader.readFile();
    }

    @Benchmark
    @BenchmarkMode({Mode.AverageTime, Mode.Throughput})
    @Warmup(iterations = 5)
    @Fork(value = 1)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void benchCalculateDistance(Blackhole bh){

        final int result = DistanceCalculator.calculateDistance(lines);

        bh.consume(result);
    }

}