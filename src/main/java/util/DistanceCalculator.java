package util;

public class DistanceCalculator {
    private final String line;

    public DistanceCalculator(String line) { // Remove a referência ao contador compartilhado
        this.line = line;
    }

    public int calculateDistance() { // Retorna o número de correspondências
        int count = 0;
        String[] arrayRatingLine = line.split(",");
        String rating = arrayRatingLine[2].replaceAll("\"", "").toLowerCase();
        String[] words = rating.split(" ");

        for (String word : words) {
            if (LevenshteinDistance.calculateDistance(word, "mouse") == 0) {
                count++;
            }
        }

        return count;
    }
}
