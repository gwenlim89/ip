package larper.task;

import java.time.LocalDate;

public class TaskDateTime {
    private LocalDate date;
    private String time;

    public TaskDateTime(LocalDate date, String time) {
        this.date = date;
        this.time = time;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
