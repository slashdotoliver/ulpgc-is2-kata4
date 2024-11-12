package software.ulpgc.readers;

import software.ulpgc.Title;

import java.io.IOException;
import java.util.List;

public interface TitleReader {

    List<Title> read() throws IOException;

}
