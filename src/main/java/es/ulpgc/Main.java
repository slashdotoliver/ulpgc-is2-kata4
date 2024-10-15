package es.ulpgc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("./src/main/resources/title.basics.tsv"));
        Map<String, Integer> histogram = new HashMap<>();
        while (true) {
            String line = reader.readLine();
            if (line == null) break;
            String[] fields = line.split("\t");
            histogram.putIfAbsent(fields[1], 0);
            histogram.compute(fields[1], (k, v) -> v + 1);
        }
        System.out.println(histogram);
    }
}
