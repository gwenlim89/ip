package larper.exception;

import larper.common.CommandHelp;

public class InvalidTimeException extends LarperException {
    public InvalidTimeException(String label) {
        super(" Larper found the " + label + " date, but no time was given.\n"
                + CommandHelp.TIME_EXAMPLES);
    }
}
