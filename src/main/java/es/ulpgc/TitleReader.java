package es.ulpgc;

import java.io.IOException;
import java.util.List;

public interface TitleReader {
    List<Title> read() throws IOException;
}
