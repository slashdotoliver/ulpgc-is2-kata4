package es.ulpgc;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        TitleReader reader = new TitleReader(new File("./src/main/resources/title.basics.tsv"), true);
        Map<Title.TitleType, Integer> histogram = new HashMap<>();
        List<Title> titles = reader.read();
        for (Title title : titles) {
            histogram.putIfAbsent(title.type(), 0);
            histogram.compute(title.type(), (k, v) -> v + 1);
        }
        System.out.println(histogram);
    }

}
