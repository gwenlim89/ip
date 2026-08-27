package larper.exception;

import larper.common.CommandHelp;

public class NoDescriptionException extends LarperException {
    public NoDescriptionException() {
        super(" Larper needs a task description before charging into battle.\n" + CommandHelp.INPUT_FORMATS);
    }
}
