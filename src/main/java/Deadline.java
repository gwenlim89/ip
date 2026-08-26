import java.time.LocalDate;

public class Deadline extends Task {
    private LocalDate byDate;
    private String byTime;

    public Deadline(String description, String byDate, String byTime) {
        this(description, LocalDate.parse(byDate), byTime);
    }

    public Deadline(String description, LocalDate byDate, String byTime) {
        super(description);
        this.byDate = byDate;
        this.byTime = byTime;
    }

    @Override
    public String getTypeIcon() {
        return "D";
    }

    @Override
    public String toFileString() {
        return super.toFileString() + " | " + byDate + " | " + byTime;
    }

    @Override
    public String toString() {
        String formattedDate = TaskDateTimeParser.formatDate(byDate);
        if (isNoTime()) {
            return super.toString() + " (by: " + formattedDate + ")";
        }
        return super.toString() + " (by: " + formattedDate + " " + byTime + ")";
    }

    private boolean isNoTime() {
        return byTime.trim().equalsIgnoreCase("no time");
    }
}
