package larper.task;

import java.time.LocalDate;

public class Event extends Task {
    private LocalDate startDate;
    private String startTime;
    private LocalDate endDate;
    private String endTime;

    public Event(String description, String startDate, String startTime, String endDate, String endTime) {
        this(description, LocalDate.parse(startDate), startTime, LocalDate.parse(endDate), endTime);
    }

    public Event(String description, LocalDate startDate, String startTime, LocalDate endDate, String endTime) {
        super(description);
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
    }

    public Event(String description, String start, String end) {
        this(description, TaskDateTimeParser.parse(start, "no time"), TaskDateTimeParser.parse(end, "no time"));
    }

    public Event(String description, TaskDateTime start, TaskDateTime end) {
        this(description, start.getDate(), start.getTime(), end.getDate(), end.getTime());
    }

    @Override
    public String getTypeIcon() {
        return "E";
    }

    @Override
    public String toFileString() {
        return super.toFileString() + " | " + startDate + " | " + startTime + " | "
                + endDate + " | " + endTime;
    }

    @Override
    public String toString() {
        return super.toString() + " (from: " + formatDateTime(startDate, startTime)
                + " to: " + formatDateTime(endDate, endTime) + ")";
    }

    private String formatDateTime(LocalDate date, String time) {
        String formattedDate = TaskDateTimeParser.formatDate(date);
        if (isNoTime(time)) {
            return formattedDate;
        }
        return formattedDate + " " + time;
    }

    private boolean isNoTime(String time) {
        return time.trim().equalsIgnoreCase("no time");
    }
}
