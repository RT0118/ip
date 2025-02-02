package fauna.exceptions;

/**
 * FaunaRuntimeException is the base class of custom
 * exceptions thrown by Fauna
 */
public class FaunaRuntimeException extends RuntimeException {
    public FaunaRuntimeException(String message) {
        super("Uuuu, " + message);
    }
}
