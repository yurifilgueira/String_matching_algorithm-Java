package util;

public class DistanceCalculator {
    private final String line;

    public DistanceCalculator(String line) { // Remove a referência ao contador compartilhado
        this.line = line;
    }

    public int calculateDistance() {
        int count = 0;
        String[] words = line.split(" ");

        for (String word : words) {
            if (LevenshteinDistance.calculateDistance(word, "mouse") == 0) {
                count++;
            }
        }

        return count;
    }
}
