package software.ulpgc.control.deserializers;

import software.ulpgc.model.Title;

import java.util.Optional;

public interface TitleDeserializer {

    Optional<Title> deserialize(String line);

}
