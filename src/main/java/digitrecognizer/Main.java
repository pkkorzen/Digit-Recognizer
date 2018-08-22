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

        List <Record> trainingRecords = retrieveRecordsFromFile("TrainingSample.csv");
        List <Record> validationRecords = retrieveRecordsFromFile("ValidationSample.csv");

        int sum = 0;
        for (int i = 0; i < validationRecords.size(); i++){
            if(validationRecords.get(i).getNumber()==predict(validationRecords.get(i).getPixels(), trainingRecords)){
                sum++;
            }
        }
        System.out.printf("Procent rozpoznanych obrazków: %.1f%s",(double)sum/500*100,"%");
    }

    public static int distance(Integer[] a, Integer[] b){
        int sum = 0;
        for(int i = 0; i < a.length; i++){
            sum+=(a[i]-b[i])*(a[i]-b[i]);
        }
        return (int)Math.sqrt(sum);
    }

    public static int predict(Integer[] pixels, List<Record> sampleRecords){
        List<Number> listOfNumbers = sampleRecords
                .stream()
                .map(x -> {
                    Number number = new Number();
                    number.setNumber(x.getNumber());
                    number.setDistance(distance(x.getPixels(), pixels));
                    return number;
                })
                .sorted(Comparator.comparingInt(x-> x.getDistance()))
                .collect(Collectors.toList());
        return listOfNumbers.get(0).getNumber();
    }

    public static List <Record> retrieveRecordsFromFile(String fileName){
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
}
