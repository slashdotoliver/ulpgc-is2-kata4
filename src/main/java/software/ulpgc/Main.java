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
    public static void main(String[] arguments) throws IOException {
        if (!validate(arguments)) exitWithErrorMessage();
        TitleReader reader = new TsvTitleReader(new File(pathFrom(arguments)), true);
        Histogram histogram = generateHistogram(reader);
        System.out.println(histogram);
    }

    private static String pathFrom(String[] arguments) {
        return arguments[0];
    }

    private static void exitWithErrorMessage() {
        System.err.println("Error: Needs exactly 1 argument <tsv_file_path>");
        System.exit(1);
    }

    private static boolean validate(String[] arguments) {
        return arguments.length == 1;
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
