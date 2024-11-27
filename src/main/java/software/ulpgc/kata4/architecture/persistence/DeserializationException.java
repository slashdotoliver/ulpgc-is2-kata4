package software.ulpgc.kata4.architecture.persistence;

public class DeserializationException extends Exception {
    public DeserializationException(Exception exception) {
        super(exception);
    }
}
