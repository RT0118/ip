package fauna.exceptions;

public class InvalidUserInputException extends FaunaRuntimeException {
    public InvalidUserInputException(String message) {
        super(message);
    }
}
