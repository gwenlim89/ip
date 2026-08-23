public class Event extends Task {
    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;

    public Event(String description, String startDate, String startTime, String endDate, String endTime) {
        super(description);
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
    }

    @Override
    public String getTypeIcon() {
        return "E";
    }

    @Override
    public String toString() {
        return super.toString() + " (from: " + startDate + " " + startTime
                + " to: " + endDate + " " + endTime + ")";
    }
}
