public class Event extends Task {
    private String start;
    private String end;

    public Event(String description, String startDate, String startTime, String endDate, String endTime) {
        super(description);
        this.start = startDate + " " + startTime;
        this.end = endDate + " " + endTime;
    }

    public Event(String description, String start, String end) {
        super(description);
        this.start = start;
        this.end = end;
    }

    @Override
    public String getTypeIcon() {
        return "E";
    }

    @Override
    public String toFileString() {
        return super.toFileString() + " | " + start + " | " + end;
    }

    @Override
    public String toString() {
        return super.toString() + " (from: " + hideNoTime(start) + " to: " + hideNoTime(end) + ")";
    }

    private String hideNoTime(String dateTime) {
        String trimmedDateTime = dateTime.trim();
        if (trimmedDateTime.toLowerCase().endsWith(" no time")) {
            return trimmedDateTime.substring(0, trimmedDateTime.length() - " no time".length()).trim();
        }
        return trimmedDateTime;
    }
}
