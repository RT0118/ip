package exceptions;

public class FaunaRuntimeException extends RuntimeException {
    public FaunaRuntimeException(String message) {
        super("Uuuu, " + message);
    }
}
