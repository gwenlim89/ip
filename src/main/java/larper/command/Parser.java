package larper.command;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import larper.exception.InvalidDateException;
import larper.exception.InvalidTimeException;
import larper.exception.LarperException;
import larper.exception.NoDescriptionException;
import larper.exception.NoTaskTypeException;
import larper.exception.NonNumberDeleteException;
import larper.task.Deadline;
import larper.task.Event;
import larper.task.Task;
import larper.task.TaskDateTime;
import larper.task.TaskDateTimeParser;
import larper.task.Todo;

/**
 * Interprets user input as Larper commands or tasks.
 */
public class Parser {
    private static final Pattern DELETE_PATTERN = Pattern.compile("\\bdelete\\b\\s+(\\S+)");
    private static final Pattern DELETE_WORD_PATTERN = Pattern.compile("\\bdelete\\b");

    private PendingTask pendingTask;

    public boolean isExitCommand(String input) {
        return input.equals("exit");
    }

    public boolean isListCommand(String input) {
        return input.equals("list");
    }

    public boolean isMarkCommand(String input) {
        return input.startsWith("mark ");
    }

    public boolean isUnmarkCommand(String input) {
        return input.startsWith("unmark ");
    }

    public boolean isDeleteCommand(String input) {
        return DELETE_WORD_PATTERN.matcher(input).find();
    }

    /**
     * Returns whether the input is the command that starts a find search.
     */
    public boolean isFindCommand(String input) {
        return input.equals("find");
    }

    public int parseMarkNumber(String input) {
        return parseTaskNumber(input.substring(5).trim());
    }

    public int parseUnmarkNumber(String input) {
        return parseTaskNumber(input.substring(7).trim());
    }

    public int parseDeleteNumber(String input) throws NonNumberDeleteException {
        Matcher matcher = DELETE_PATTERN.matcher(input);
        if (!matcher.find()) {
            throw new NonNumberDeleteException();
        }

        try {
            return Integer.parseInt(matcher.group(1));
        } catch (NumberFormatException e) {
            throw new NonNumberDeleteException();
        }
    }

    public Task parseTask(String input) throws LarperException {
        if (pendingTask != null && !startsWithTaskCommand(input)) {
            return completePendingTask(input);
        }

        pendingTask = null;
        return createTask(input);
    }

    private Task createTask(String input) throws LarperException {
        if (input.isEmpty()) {
            throw new NoTaskTypeException();
        }

        if (input.equals("todo") || input.equals("deadline") || input.equals("event")) {
            throw new NoDescriptionException();
        }

        if (input.startsWith("todo ")) {
            String description = input.substring(5).trim();
            if (description.isEmpty() || countSlashes(description) != 0) {
                throw new NoDescriptionException();
            }
            return new Todo(description);
        }

        if (input.startsWith("deadline ")) {
            String taskInfo = input.substring(9).trim();
            int byIndex = taskInfo.indexOf("/by");
            if (byIndex == -1) {
                throw new InvalidDateException("deadline");
            }
            String description = taskInfo.substring(0, byIndex).trim();
            String by = taskInfo.substring(byIndex + 3).trim();
            if (description.isEmpty()) {
                throw new NoDescriptionException();
            }
            TaskDateTime byDateTime = parseTaskDateTime(by, "deadline", "deadline", description, null, null);
            return new Deadline(description, byDateTime.getDate(), byDateTime.getTime());
        }

        if (input.startsWith("event ")) {
            String taskInfo = input.substring(6).trim();
            int fromIndex = taskInfo.indexOf("/from");
            int toIndex = taskInfo.indexOf("/to");
            if (fromIndex == -1 || toIndex == -1 || fromIndex > toIndex) {
                throw new InvalidDateException("event start or end");
            }
            String description = taskInfo.substring(0, fromIndex).trim();
            String from = taskInfo.substring(fromIndex + 5, toIndex).trim();
            String to = taskInfo.substring(toIndex + 3).trim();
            if (description.isEmpty()) {
                throw new NoDescriptionException();
            }
            if (countSlashes(taskInfo) != 2) {
                throw new InvalidDateException("event start or end");
            }
            TaskDateTime startDateTime = parseTaskDateTime(from, "event start", "event-start",
                    description, to, null);
            TaskDateTime endDateTime = parseTaskDateTime(to, "event end", "event-end",
                    description, startDateTime.getDate().toString(), startDateTime.getTime());
            return new Event(description, startDateTime, endDateTime);
        }

        throw new NoTaskTypeException();
    }

    private Task completePendingTask(String input) throws LarperException {
        String time = input.trim();
        if (time.isEmpty() || !isValidTimeAnswer(time)) {
            throw new InvalidTimeException(pendingTask.waitingFor);
        }

        PendingTask taskToComplete = pendingTask;
        pendingTask = null;

        if (taskToComplete.type.equals("deadline")) {
            String normalizedTime = TaskDateTimeParser.normalizeTime(time);
            return new Deadline(taskToComplete.description, LocalDate.parse(taskToComplete.firstDate), normalizedTime);
        }

        if (taskToComplete.type.equals("event-start")) {
            String normalizedTime = TaskDateTimeParser.normalizeTime(time);
            TaskDateTime endDateTime = parseTaskDateTime(taskToComplete.secondDate, "event end", "event-end",
                    taskToComplete.description, taskToComplete.firstDate, normalizedTime);
            return new Event(taskToComplete.description, LocalDate.parse(taskToComplete.firstDate), normalizedTime,
                    endDateTime.getDate(), endDateTime.getTime());
        }

        if (taskToComplete.type.equals("event-end")) {
            String normalizedTime = TaskDateTimeParser.normalizeTime(time);
            return new Event(taskToComplete.description, taskToComplete.firstDate, taskToComplete.firstTime,
                    taskToComplete.secondDate, normalizedTime);
        }

        throw new NoTaskTypeException();
    }

    private TaskDateTime parseTaskDateTime(String text, String label, String pendingType, String description,
            String extraDate, String extraTime)
            throws InvalidDateException, InvalidTimeException {
        TaskDateTime taskDateTime;
        try {
            taskDateTime = TaskDateTimeParser.parse(text, "");
        } catch (RuntimeException e) {
            throw new InvalidDateException(label);
        }

        if (taskDateTime.getTime().isEmpty()) {
            if (pendingType.equals("event-end")) {
                pendingTask = new PendingTask(pendingType, description, extraDate, extraTime,
                        taskDateTime.getDate().toString(), label);
            } else {
                pendingTask = new PendingTask(pendingType, description, taskDateTime.getDate().toString(),
                        extraTime, extraDate, label);
            }
            throw new InvalidTimeException(label);
        }

        return taskDateTime;
    }

    private boolean isValidTimeAnswer(String text) {
        return TaskDateTimeParser.isValidTimeAnswer(text);
    }

    private boolean startsWithTaskCommand(String input) {
        return input.startsWith("todo ") || input.startsWith("deadline ") || input.startsWith("event ")
                || input.equals("todo") || input.equals("deadline") || input.equals("event");
    }

    private int countSlashes(String input) {
        int slashCount = 0;
        int index = 0;
        while (index < input.length()) {
            if (input.charAt(index) == '/') {
                slashCount++;
            }
            index++;
        }
        return slashCount;
    }

    private int parseTaskNumber(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static class PendingTask {
        private String type;
        private String description;
        private String firstDate;
        private String firstTime;
        private String secondDate;
        private String waitingFor;

        public PendingTask(String type, String description, String firstDate, String firstTime,
                String secondDate, String waitingFor) {
            this.type = type;
            this.description = description;
            this.firstDate = firstDate;
            this.firstTime = firstTime;
            this.secondDate = secondDate;
            this.waitingFor = waitingFor;
        }
    }
}
