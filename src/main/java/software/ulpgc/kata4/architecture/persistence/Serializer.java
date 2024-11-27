package software.ulpgc.kata4.architecture.persistence;

public interface Serializer<Input, Target> {
    Target serialize(Input title) throws SerializationException;
}
