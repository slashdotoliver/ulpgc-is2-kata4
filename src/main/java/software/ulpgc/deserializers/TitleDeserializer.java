package software.ulpgc.deserializers;

import software.ulpgc.Title;

import java.util.Optional;

public interface TitleDeserializer {

    Optional<Title> deserialize(String line);

}
