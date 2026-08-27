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
    }

    /**
     * Marks this task as not done.
     */
    public void unmarkAsDone() {
        isDone = false;
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
        return "[" + getTypeIcon() + "][" + getStatusIcon() + "] " + description;
    }
}
