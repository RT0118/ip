package fauna.exceptions;

/**
 * TaskListException are exceptions thrown by TaskList
 */
public class TaskListException extends FaunaRuntimeException {
    public TaskListException(String message) {
        super(message);
    }
}
