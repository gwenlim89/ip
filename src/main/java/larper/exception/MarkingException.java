package larper.exception;

/**
 * Signals that the user tried to mark a task that is already done.
 */
public class MarkingException extends LarperException {
    /**
     * Creates an exception for marking an already done task.
     */
    public MarkingException() {
        super(" This one is already done. No need to perform extra productivity.");
    }
}
