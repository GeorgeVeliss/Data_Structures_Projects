import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class createRandomDataFiles {

    public static void main(String[] args) {

        Random rand = new Random();
        File dataDir = new File("data");
        dataDir.mkdir();
        int[] NValues = { 100, 500, 1000 };
        for (int N : NValues) {
            for (int i = 1; i <= 10; i++) {
                try {
                    File dataFile = new File(dataDir, "N = " + N + " - " + i + ".txt");
                    FileWriter writer = new FileWriter(dataFile);
                    for (int j = 0; j < N; j++)
                        writer.write(rand.nextInt(1_000_000) + "\n");
                    writer.close();
                } catch (IOException IOException) {
                    System.out.println("An IO error occurred.");
                    IOException.printStackTrace();
                }
            }
        }
    }
}