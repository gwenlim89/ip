package larper.storage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;

import larper.task.Deadline;
import larper.task.Event;
import larper.task.Task;
import larper.task.TaskDateTime;
import larper.task.TaskDateTimeParser;
import larper.task.Todo;

public class Storage {
    private Path filePath;

    public Storage(Path filePath) {
        this.filePath = filePath;
    }

    public void saveTasks(ArrayList<Task> tasks) throws IOException {
        Path parentDirectory = filePath.getParent();
        if (parentDirectory != null) {
            Files.createDirectories(parentDirectory);
        }

        ArrayList<String> lines = new ArrayList<>();
        for (Task task : tasks) {
            lines.add(task.toFileString());
        }

        Files.write(filePath, lines, StandardCharsets.UTF_8);
    }

    public ArrayList<Task> loadTasks() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();
        if (!Files.exists(filePath)) {
            return tasks;
        }

        ArrayList<String> lines = new ArrayList<>(Files.readAllLines(filePath, StandardCharsets.UTF_8));
        for (String line : lines) {
            if (!line.isBlank()) {
                try {
                    tasks.add(parseTask(line));
                } catch (RuntimeException e) {
                    continue;
                }
            }
        }
        return tasks;
    }

    private Task parseTask(String line) {
        String[] parts = line.split("\\s*\\|\\s*");
        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];
        Task task;

        if (type.equals("T")) {
            task = new Todo(description);
        } else if (type.equals("D")) {
            task = parseDeadline(description, parts);
        } else {
            task = parseEvent(description, parts);
        }

        task.setDone(isDone);
        return task;
    }

    private Deadline parseDeadline(String description, String[] parts) {
        if (parts.length >= 5) {
            return new Deadline(description, TaskDateTimeParser.parseDate(parts[3]),
                    TaskDateTimeParser.normalizeTime(parts[4]));
        }

        TaskDateTime deadlineDateTime = TaskDateTimeParser.parse(parts[3], "no time");
        return new Deadline(description, deadlineDateTime.getDate(), deadlineDateTime.getTime());
    }

    private Event parseEvent(String description, String[] parts) {
        if (parts.length >= 7) {
            return new Event(description, LocalDate.parse(parts[3]), TaskDateTimeParser.normalizeTime(parts[4]),
                    LocalDate.parse(parts[5]), TaskDateTimeParser.normalizeTime(parts[6]));
        }

        return new Event(description, parts[3], parts[4]);
    }
}
