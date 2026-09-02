package larper.exception;

/**
 * Signals that the user tried to delete a task from an empty task list.
 */
public class EmptyDeletionException extends LarperException {
    /**
     * Creates an exception for deleting from an empty task list.
     */
    public EmptyDeletionException() {
        super(" Quest log is empty. Nothing to delete here!!!");
    }
}
