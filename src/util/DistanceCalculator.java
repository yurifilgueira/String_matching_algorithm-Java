
package util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DistanceCalculator implements Runnable{

    private List<String> lines;


    public DistanceCalculator() {
    }

    public DistanceCalculator(List<String> lines) {
        this.lines = lines;
    }

    public void calculateDistance() {

        System.out.println(Thread.currentThread().getName() + " -> started.");

        int count = 0;
        for (String line : lines) {

            String[] words = line.split(" ");

            if (LevenshteinDistance.calculateDistance(line, "mouse") == 0){
                    count += 1;
            }
        }

        System.out.println(Thread.currentThread().getName() + " -> finished.");
        ResultSaver.save(count);

    }

    @Override
    public void run() {
        calculateDistance();
    }
}
