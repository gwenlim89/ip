package larper.exception;

import larper.common.CommandHelp;

public class InvalidDateException extends LarperException {
    public InvalidDateException(String label) {
        super(" Where the date is? Larper needs the " + label + " date.\n" + CommandHelp.DATE_EXAMPLES);
    }
}
