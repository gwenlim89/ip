package larper.exception;

import larper.common.CommandHelp;

public class NoTaskTypeException extends LarperException {
    public NoTaskTypeException() {
        super(" oh watchu yapping on\n" + CommandHelp.INPUT_FORMATS);
    }
}
