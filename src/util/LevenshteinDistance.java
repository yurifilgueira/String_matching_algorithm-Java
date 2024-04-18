package util;

public class LevenshteinDistance {

    private String origin;
    private String destiny;

    public LevenshteinDistance() {
    }

    public LevenshteinDistance(String origin, String destiny) {
        this.origin = origin;
        this.destiny = destiny;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestiny() {
        return destiny;
    }

    public void setDestiny(String destiny) {
        this.destiny = destiny;
    }

    public static Integer calculateDistance(String origin, String destiny){
        int rows = origin.length();
        int columns = destiny.length();
        int[][] distance = new int[rows + 1][columns + 1];

        for (int i = 0; i <= rows; i++) {
            distance[i][0] = i;
        }

        for (int i = 0; i <= columns; i++) {
            distance[0][i] = i;
        }

        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= columns; j++) {
                if (origin.charAt(i - 1) == destiny.charAt(j - 1)){
                    distance[i][j] = distance[i - 1][j - 1];
                }
                else {
                    distance[i][j] = 1 + Math.min(
                            distance[i][j - 1],
                            Math.min(
                                    distance[i - 1][j],
                                    distance[i - 1][j - 1]
                            )
                    );
                }
            }
        }

        return distance[rows][columns];

    }
}
