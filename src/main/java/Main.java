import util.DatasetReader;
import util.DistanceCalculator;
import util.ResultSaver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException {

        List<Thread> threads = new ArrayList<>();

        long startTime = System.currentTimeMillis();
        var lines = DatasetReader.readFile();

        System.out.println("Starting threads...");

        AtomicInteger totalCounter = new AtomicInteger(0);

        lines.parallelStream()
                .map(DistanceCalculator::new)
                .mapToInt(DistanceCalculator::calculateDistance)
                .forEach(totalCounter::addAndGet);

        System.out.println("Total de ocorrências da palavra 'mouse': " + totalCounter.get());

        System.out.println("Total read and print time: " + (double) (System.currentTimeMillis() - startTime) / 1000);

        ResultSaver.save(totalCounter);
    }
}
