package larper.task;

/**
 * Represents a task with a description and completion status.
 */
public class Task {
    private String description;
    private boolean isDone;

    /**
     * Creates an unmarked task with the specified description.
     *
     * @param description Text that describes the task.
     */
    public Task(String description) {
        assert description != null && !description.isBlank() : "Task description should be provided before creation.";
        this.description = description;
        this.isDone = false;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return isDone;
    }

    /**
     * Marks this task as done.
     */
    public void markAsDone() {
        isDone = true;
        assert isDone : "Task should be marked after markAsDone runs.";
    }

    /**
     * Marks this task as not done.
     */
    public void unmarkAsDone() {
        isDone = false;
        assert !isDone : "Task should be unmarked after unmarkAsDone runs.";
    }

    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

    public String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    /**
     * Returns the task type icon used in console and file output.
     */
    public String getTypeIcon() {
        return "?";
    }

    /**
     * Returns this task in the storage file format.
     */
    public String toFileString() {
        assert getTypeIcon() != null && !getTypeIcon().isBlank() : "Task type icon should be available for storage.";
        return getTypeIcon() + " | " + getDoneStatusForFile() + " | " + description;
    }

    private String getDoneStatusForFile() {
        return isDone ? "1" : "0";
    }

    /**
     * Returns this task in the console display format.
     */
    @Override
    public String toString() {
        assert getTypeIcon() != null && !getTypeIcon().isBlank() : "Task type icon should be available for display.";
        return "[" + getTypeIcon() + "][" + getStatusIcon() + "] " + description;
    }
}
