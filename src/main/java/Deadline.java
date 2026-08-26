public class Deadline extends Task {
    private String by;

    public Deadline(String description, String byDate, String byTime) {
        super(description);
        this.by = byDate + " " + byTime;
    }

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String getTypeIcon() {
        return "D";
    }

    @Override
    public String toFileString() {
        return super.toFileString() + " | " + by;
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + hideNoTime(by) + ")";
    }

    private String hideNoTime(String dateTime) {
        String trimmedDateTime = dateTime.trim();
        if (trimmedDateTime.toLowerCase().endsWith(" no time")) {
            return trimmedDateTime.substring(0, trimmedDateTime.length() - " no time".length()).trim();
        }
        return trimmedDateTime;
    }
}
