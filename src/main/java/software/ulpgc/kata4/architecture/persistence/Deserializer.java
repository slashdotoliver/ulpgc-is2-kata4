package software.ulpgc.kata4.architecture.persistence;

public interface Deserializer<Input, Target> {
    Target deserialize(Input input) throws DeserializationException;
}
