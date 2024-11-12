package software.ulpgc;

import software.ulpgc.readers.TitleReader;
import software.ulpgc.readers.files.TsvTitleReader;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        TitleReader reader = new TsvTitleReader(new File("./title.basics.tsv"), true);
        Map<Title.TitleType, Integer> histogram = generateHistogram(reader);
        System.out.println(histogram);
    }

    private static Map<Title.TitleType, Integer> generateHistogram(TitleReader reader) throws IOException {
        List<Title> titles = reader.read();
        return HistogramGenerator.generate(titles);
    }

}
