import util.LevenshteinDistance;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static final String RATINGS = "C:\\Users\\yuris\\Downloads\\archive\\train.csv";

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        Scanner sc = new Scanner(System.in);

        String w = sc.nextLine();

        int count = 0;
        int matches = 0;
        try(BufferedReader bfRating = new BufferedReader(new FileReader(RATINGS))){

            String ratingLine = bfRating.readLine();
            count++;

            while (ratingLine != null) {

                String[] arrayRatingLine = ratingLine .split(",");
                String rating = arrayRatingLine[2].replaceAll("\"", "").toLowerCase();
                String[] words = rating.split(" ");

                for (String s : words) {
                    int distance = LevenshteinDistance.calculateDistance(s, w);
                    if (distance <= 1){
                        matches++;
                        System.out.println(s);
                        System.out.println("Matches: " + matches);
                        break;
                    }

                }

                ratingLine = bfRating.readLine();
                count++;
                // System.out.println("Line: " + count);

            }

        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }

        System.out.println("Total read and print time: " + (double) (System.currentTimeMillis() - startTime) / 60000);
        System.out.println("Count: " + count);
        System.out.println("Total matches: " + matches);
    }
}