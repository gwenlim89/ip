package larper.exception;

/**
 * Signals that the user tried to unmark a task that is already not done.
 */
public class UnmarkingException extends LarperException {
    /**
     * Creates an exception for unmarking an already unmarked task.
     */
    public UnmarkingException() {
        super(" This task is already unmarked. Quit messing around and pick a done task.");
    }
}
