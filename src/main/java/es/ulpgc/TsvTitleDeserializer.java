package es.ulpgc;

import java.time.Duration;
import java.time.Year;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TsvTitleDeserializer implements TitleDeserializer {
    @Override
    public Optional<Title> deserialize(String line) {
        String[] columns = line.split("\t");
        try {
            return Optional.of(new Title(
                    columns[0],
                    type(columns[1]),
                    columns[2],
                    columns[3],
                    isAdult(columns[4]),
                    year(columns[5]),
                    endYear(columns[6]),
                    duration(columns[7]),
                    genres(columns[8])
            ));
        } catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    private Optional<Duration> duration(String minutes) {
        try {
            return Optional.of(Duration.parse("PT" + minutes + "M"));
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }

    private Optional<Year> endYear(String column) {
        try {
            return Optional.of(Year.parse(column));
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }

    private Year year(String column) {
        return Year.parse(column);
    }

    private boolean isAdult(String column) {
        return format(column).equals("1");
    }

    private Title.TitleType type(String column) {
        return Title.TitleType.valueOf(format(column));
    }

    private static List<Title.Genre> genres(String column) {
        List<Title.Genre> genres = new ArrayList<>();
        for (String s : column.split(",")) {
            genres.add(Title.Genre.valueOf(format(s)));
        }
        return genres;
    }

    private static String format(String s) {
        return s.trim().toUpperCase();
    }
}
