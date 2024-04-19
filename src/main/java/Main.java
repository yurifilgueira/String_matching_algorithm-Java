import util.DatasetReader;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        final String PATH = "resources\\train.csv";

        long startTime = System.currentTimeMillis();

        List<String> items = new ArrayList<>(List.of("logitech", "keyboard", "mouse", "hyperx", "razer", "lenovo", "acer", "lg", "samsung", "laptop"));

        DatasetReader.readDatasetAndProcess(PATH, items);

        System.out.println("Total read and print time: " + (double) (System.currentTimeMillis() - startTime) / 60000);
    }
}
