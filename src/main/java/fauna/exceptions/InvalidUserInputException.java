package fauna.exceptions;

/**
 * InvalidUserInputException are exceptions thrown by parser-related classes
 */
public class InvalidUserInputException extends FaunaRuntimeException {
    public InvalidUserInputException(String message) {
        super(message);
    }
}
