package software.ulpgc.kata4.architecture.persistence;

public class SerializationException extends Exception {
    public SerializationException(Exception exception) {
        super(exception);
    }
}
