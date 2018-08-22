package digitrecognizer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();
        List<Record> trainingRecords = retrieveRecordsFromFile("TrainingSample.csv");
        List<Record> validationRecords = retrieveRecordsFromFile("ValidationSample.csv");

        int sum = 0;
        for (Record record : validationRecords){
            if (record.getNumber() == predict(record.getPixels(), trainingRecords)){
                sum++;
            }
        }

        System.out.printf("Procent rozpoznanych obrazków: %.1f%s", (double) sum / 500 * 100, "%");
        System.out.println();
        long endTime = System.currentTimeMillis();
        System.out.println("Time stream: " + (endTime - startTime));

        //same calculations as above using parallelStream this time
        long startTimeParallel = System.currentTimeMillis();
        List<Record> trainingRecordsParallel = retrieveRecordsFromFileParallel("TrainingSample.csv");
        List<Record> validationRecordsParallel = retrieveRecordsFromFileParallel("ValidationSample.csv");

        int sumParallel = 0;

        for (Record record : validationRecordsParallel){
            if (record.getNumber() == predictParalell(record.getPixels(), trainingRecordsParallel)){
                sumParallel++;
            }
        }

        System.out.printf("Procent rozpoznanych obrazków(parallel): %.1f%s", (double) sumParallel / 500 * 100, "%");
        System.out.println();
        long endTimeParallel = System.currentTimeMillis();
        System.out.println("Time parallelstream: " + (endTimeParallel - startTimeParallel));
    }

    public static int distance(Integer[] a, Integer[] b) {
        int sum = 0;
        for (int i = 0; i < a.length; i++) {
            sum += (a[i] - b[i]) * (a[i] - b[i]);
        }
        return (int) Math.sqrt(sum);
    }

    public static int predict(Integer[] pixels, List<Record> sampleRecords) {
        List<Number> listOfNumbers = sampleRecords
                .stream()
                .map(x -> {
                    Number number = new Number();
                    number.setNumber(x.getNumber());
                    number.setDistance(distance(x.getPixels(), pixels));
                    return number;
                })
                .sorted(Comparator.comparingInt(x -> x.getDistance()))
                .collect(Collectors.toList());
        return listOfNumbers.get(0).getNumber();
    }

    public static List<Record> retrieveRecordsFromFile(String fileName) {
        Path path = Paths.get(fileName);

        List<String> listOfLines = new ArrayList<>();
        try {
            listOfLines = Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Nie znaleziono pliku");
        }
        List<Record> records = listOfLines.subList(1, listOfLines.size())
                .stream()
                .map(x -> x.split(","))
                .map(x -> {
                    return Arrays.stream(x)
                            .map(y -> Integer.parseInt(y))
                            .collect(Collectors.toList());
                })
                .map(x -> {
                    Record record = new Record();
                    record.setNumber(x.get(0));
                    record.setPixels(x.subList(1, x.size()).toArray(new Integer[0]));
                    return record;
                })
                .collect(Collectors.toList());
        return records;

    }

    //adding same methods as above using paralellStream this time to calculate the difference between stream and parallelStream
    public static int predictParalell(Integer[] pixels, List<Record> sampleRecords) {
        List<Number> listOfNumbers = sampleRecords
                .parallelStream()
                .map(x -> {
                    Number number = new Number();
                    number.setNumber(x.getNumber());
                    number.setDistance(distance(x.getPixels(), pixels));
                    return number;
                })
                .sorted(Comparator.comparingInt(x -> x.getDistance()))
                .collect(Collectors.toList());
        return listOfNumbers.get(0).getNumber();
    }

    public static List<Record> retrieveRecordsFromFileParallel(String fileName) {
        Path path = Paths.get(fileName);

        List<String> listOfLines = new ArrayList<>();
        try {
            listOfLines = Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Nie znaleziono pliku");
        }
        List<Record> records = listOfLines.subList(1, listOfLines.size())
                .parallelStream()
                .map(x -> x.split(","))
                .map(x -> {
                    return Arrays.stream(x)
                            .map(y -> Integer.parseInt(y))
                            .collect(Collectors.toList());
                })
                .map(x -> {
                    Record record = new Record();
                    record.setNumber(x.get(0));
                    record.setPixels(x.subList(1, x.size()).toArray(new Integer[0]));
                    return record;
                })
                .collect(Collectors.toList());
        return records;

    }
}
