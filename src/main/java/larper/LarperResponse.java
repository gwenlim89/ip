package larper;

/**
 * Stores Larper's reply to one user input.
 * The response also records whether the current session should end after the reply is shown.
 */
public class LarperResponse {
    private final String message;
    private final boolean isExit;

    /**
     * Creates a response with its message and exit status.
     *
     * @param message Text Larper should show to the user.
     * @param isExit Whether this response should end the current session.
     */
    public LarperResponse(String message, boolean isExit) {
        this.message = message;
        this.isExit = isExit;
    }

    public String getMessage() {
        return message;
    }

    public boolean isExit() {
        return isExit;
    }
}
