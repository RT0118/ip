package fauna.exceptions;

/**
 * StorageException are exceptions thrown by storage-related classes
 */
public class StorageException extends RuntimeException {
    public StorageException(String message) {
        super(message);
    }
}
