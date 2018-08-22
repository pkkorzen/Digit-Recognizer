package digitrecognizer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Path path = Paths.get("TrainingSample.csv");

        List<String> listOfPixels = new ArrayList<>();
        try {
            listOfPixels = Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Nie znaleziono pliku");
        }
    }
}