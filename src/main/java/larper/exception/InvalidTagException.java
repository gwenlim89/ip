package larper.exception;

/**
 * Reports that a task tag does not contain one valid word.
 */
public class InvalidTagException extends LarperException {
    private static final String MESSAGE = " Larper needs tags to be one word, like #school. Try that again.";

    /**
     * Creates an exception for an invalid task tag.
     */
    public InvalidTagException() {
        super(MESSAGE);
    }
}
