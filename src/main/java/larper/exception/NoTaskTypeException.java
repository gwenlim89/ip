package larper.exception;

import larper.common.CommandHelp;

/**
 * Signals that user input does not start with a supported task or command type.
 */
public class NoTaskTypeException extends LarperException {
    /**
     * Creates an exception for input that does not identify a supported task type.
     */
    public NoTaskTypeException() {
        super(" oh watchu yapping on, give Larper a real command\n" + CommandHelp.INPUT_FORMATS);
    }
}
