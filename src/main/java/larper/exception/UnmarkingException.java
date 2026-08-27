package larper.exception;

public class UnmarkingException extends LarperException {
    public UnmarkingException() {
        super(" This task is already unmarked. Quit messing around and pick a done task.");
    }
}
