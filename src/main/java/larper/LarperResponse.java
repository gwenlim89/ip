package larper;

/**
 * Represents Larper's reply to one user input.
 */
public class LarperResponse {
    private String message;
    private boolean isExit;

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
