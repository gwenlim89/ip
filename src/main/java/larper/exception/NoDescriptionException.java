package larper.exception;

import larper.common.CommandHelp;

/**
 * Signals that a task command is missing its task description.
 */
public class NoDescriptionException extends LarperException {
    /**
     * Creates an exception for task commands without a usable description.
     */
    public NoDescriptionException() {
        super(" Larper needs a task description before charging into battle.\n" + CommandHelp.INPUT_FORMATS);
    }
}
