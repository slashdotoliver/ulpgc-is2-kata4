package es.ulpgc;

import java.util.ArrayList;
import java.util.List;

public class TitleDeserializer {
    public Title deserialize(String line) {
        String[] columns = line.split("\t");
        return new Title(
                columns[0],
                type(columns[1]),
                columns[2],
                columns[3],
                isAdult(columns[4]),
                year(columns[5]),
                endYear(columns[6]),
                duration(columns[7]),
                genres(columns[8])
        );
    }

    private static List<Title.Genre> genres(String column) {
        List<Title.Genre> genres = new ArrayList<>();
        for (String s : column.split(",")) {
            genres.add(Title.Genre.valueOf(s.trim()));
        }
        return genres;
    }
}
