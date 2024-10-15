package es.ulpgc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TitleReader {
    private final File file;
    private final boolean hasHeader;

    public TitleReader(File file, boolean hasHeader) {
        this.file = file;
        this.hasHeader = hasHeader;
    }

    public List<Title> read() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        if (hasHeader) readHeaderWith(reader);
        List<Title> titles =new ArrayList<>();
        while (true) {
            String line = reader.readLine();
            if (line == null) break;
            titles.add(new TitleDeserializer().deserialize(line));
        }
        return titles;
    }

    private void readHeaderWith(BufferedReader reader) throws IOException {
        reader.readLine();
    }
}
