package larper.task;

import java.time.LocalDate;

/**
 * Represents a task that should be completed by a specified date and optional time.
 */
public class Deadline extends Task {
    private LocalDate byDate;
    private String byTime;

    /**
     * Creates an unmarked deadline task using an ISO date string.
     *
     * @param description Text that describes the deadline task.
     * @param byDate Deadline date in ISO format.
     * @param byTime Deadline time in normalized form, or "no time".
     */
    public Deadline(String description, String byDate, String byTime) {
        this(description, LocalDate.parse(byDate), byTime);
    }

    /**
     * Creates an unmarked deadline task using a parsed date.
     *
     * @param description Text that describes the deadline task.
     * @param byDate Deadline date.
     * @param byTime Deadline time in normalized form, or "no time".
     */
    public Deadline(String description, LocalDate byDate, String byTime) {
        super(description);
        this.byDate = byDate;
        this.byTime = byTime;
    }

    /**
     * Returns the type icon for deadline tasks.
     */
    @Override
    public String getTypeIcon() {
        return "D";
    }

    /**
     * Returns this deadline in the storage file format.
     */
    @Override
    public String toFileString() {
        return super.toFileString() + " | " + byDate + " | " + byTime;
    }

    /**
     * Returns this deadline in the console display format.
     */
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
