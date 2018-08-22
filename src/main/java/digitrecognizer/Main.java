package digitrecognizer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

        List<String[]> listOfLines = listOfPixels
                .stream()
                .map(x -> x.split(","))
                .collect(Collectors.toList());

        List<String[]> listOfData = listOfLines.subList(1, listOfLines.size());

        List<List<Integer>> listOfIntegers = listOfData
                .stream()
                .map(x -> {
                    return Arrays.stream(x)
                            .map(y -> Integer.parseInt(y))
                            .collect(Collectors.toList());
                })
                .collect(Collectors.toList());

        List <Record> listOfRecords = listOfIntegers
                .stream()
                .map(x -> {
                    Record record = new Record();
                    record.Number = x.get(0);
                    record.Pixels = x.subList(1, x.size()).toArray(new Integer[0]);
                    return record;
                })
                .collect(Collectors.toList());
    }
}
