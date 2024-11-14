package software.ulpgc;

import software.ulpgc.control.HistogramGenerator;
import software.ulpgc.control.readers.TitleReader;
import software.ulpgc.control.readers.files.TsvTitleReader;
import software.ulpgc.model.Histogram;
import software.ulpgc.model.Title;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        TitleReader reader = new TsvTitleReader(new File("./title.basics.tsv"), true);
        Histogram histogram = generateHistogram(reader);
        System.out.println(histogram);
    }

    private static Histogram generateHistogram(TitleReader reader) throws IOException {
        List<Title> titles = reader.read();
        return HistogramGenerator.generateBy(
                title -> title.type().name(),
                titles,
                "Histogram of Type of Titles"
        );
    }

}
