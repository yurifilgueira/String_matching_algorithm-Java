package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;

import static java.nio.file.Files.newBufferedReader;

public class DatasetReader implements Runnable{

    private final static String PATH = "resources\\train.csv";

    private String item;
    private String item2;
    private ConcurrentHashMap matches;

    public DatasetReader() {
    }

    public DatasetReader(String item, ConcurrentHashMap matches) {
        this.item = item;
        this.matches = matches;
    }

    public DatasetReader(String item, String item2, ConcurrentHashMap matches) {
        this.item = item;
        this.item2 = item2;
        this.matches = matches;
    }

    public void readDatasetAndProcess() {

        try (BufferedReader br = newBufferedReader(Paths.get(PATH))) {

            int counter = 0;
            String line;
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + " começou.");
            while ((line = br.readLine()) != null) {
                counter++;
                String[] arrayRatingLine = line.split(",");
                String rating = arrayRatingLine[2].replaceAll("\"", "").toLowerCase();
                String[] words = rating.split(" ");

                for (String word : words) {
                    if (LevenshteinDistance.calculateDistance(item, word) == 0){
                        matches.put(word, (int) matches.getOrDefault(word, 0) + 1);
                        System.out.println("Match: " + item + " - " + word);
                        break;
                    }
                    else if (LevenshteinDistance.calculateDistance(item2, word) == 0){
                        matches.put(word, (int) matches.getOrDefault(word, 0) + 1);
                        System.out.println("Match: " + item2 + " - " + word);
                        break;
                    }
                }
            }

            System.out.println("Count: " + counter);
            ResultSaver.save(matches);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        readDatasetAndProcess();
        System.out.println("Total read and print time of " + Thread.currentThread().getName() +": " + (double) (System.currentTimeMillis() - startTime) / 60000);
    }
}