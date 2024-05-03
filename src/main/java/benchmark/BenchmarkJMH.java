package benchmark;

import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class BenchmarkJMH {

    public static void main(String[] args) throws Exception {
        // org.openjdk.jmh.Main.main(args);

        Options opt = new OptionsBuilder()
                .include(BenchTest.class.getSimpleName())
                .warmupIterations(5)
                .shouldDoGC(true)
                .measurementIterations(5).forks(1)
                .jvmArgs("-server", "-Xms2048m", "-Xmx2048m")
                .mode(Mode.All).build();
        new Runner(opt).run();
    }
}
