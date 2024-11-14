package software.ulpgc.control.deserializers.files;

import software.ulpgc.model.Title;
import software.ulpgc.control.deserializers.TitleDeserializer;

import java.time.Duration;
import java.time.Year;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TsvTitleDeserializer implements TitleDeserializer {

    private static final String EMPTY_FIELD = "\\N";

    @Override
    public Optional<Title> deserialize(String line) {
        String[] columns = line.split("\t");
        try {
            return Optional.of(new Title(
                    columns[0],
                    type(format(columns[1])),
                    columns[2],
                    columns[3],
                    isAdult(format(columns[4])),
                    year(format(columns[5])),
                    year(format(columns[6])),
                    duration(format(columns[7])),
                    genres(format(columns[8]))
            ));
        } catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    private static String format(String value) {
        return value.trim().toUpperCase().replace('-', '_');
    }

    private static List<Title.Genre> genres(String fields) throws IllegalArgumentException {
        if (fields.equals(EMPTY_FIELD)) return Collections.emptyList();
        return Arrays
                .stream(fields.split(","))
                .map(Title.Genre::valueOf)
                .toList();
    }

    private static Title.TitleType type(String column) throws IllegalArgumentException {
        return Title.TitleType.valueOf(column);
    }

    private static Optional<Duration> duration(String minutes) throws DateTimeParseException {
        return minutes.equals(EMPTY_FIELD) ? Optional.empty() : Optional.of(Duration.parse("PT" + minutes + "M"));
    }

    private static Optional<Year> year(String column) throws DateTimeParseException {
        return column.equals(EMPTY_FIELD) ? Optional.empty() : Optional.of(Year.parse(column));
    }

    private static boolean isAdult(String column) {
        return column.equals("1");
    }
}
