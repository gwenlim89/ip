package larper.exception;

public class InvalidNumberDeleteException extends LarperException {
    public InvalidNumberDeleteException(int taskCount) {
        super(" The number provided is invalid. Try again.\n"
                + " You have " + taskCount + " task(s) in the list, so the number must be from 1 to "
                + taskCount + ".");
    }
}
