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
 * Interprets raw user input as Larper commands or task objects.
 */
public class Parser {
    private static final String EXIT_COMMAND = "exit";
    private static final String LIST_COMMAND = "list";
    private static final String FIND_COMMAND = "find";
    private static final String MARK_COMMAND_PREFIX = "mark ";
    private static final String UNMARK_COMMAND_PREFIX = "unmark ";
    private static final String TODO_COMMAND = "todo";
    private static final String DEADLINE_COMMAND = "deadline";
    private static final String EVENT_COMMAND = "event";
    private static final String TODO_COMMAND_PREFIX = TODO_COMMAND + " ";
    private static final String DEADLINE_COMMAND_PREFIX = DEADLINE_COMMAND + " ";
    private static final String EVENT_COMMAND_PREFIX = EVENT_COMMAND + " ";
    private static final String BY_MARKER = "/by";
    private static final String FROM_MARKER = "/from";
    private static final String TO_MARKER = "/to";
    private static final String PENDING_DEADLINE = "deadline";
    private static final String PENDING_EVENT_START = "event-start";
    private static final String PENDING_EVENT_END = "event-end";
    private static final int EXPECTED_EVENT_SLASH_COUNT = 2;
    private static final Pattern DELETE_PATTERN = Pattern.compile("\\bdelete\\b\\s+(\\S+)");
    private static final Pattern DELETE_WORD_PATTERN = Pattern.compile("\\bdelete\\b");

    private PendingTask pendingTask;

    /**
     * Returns whether the input is the command that exits Larper.
     */
    public boolean isExitCommand(String input) {
        return input.equals(EXIT_COMMAND);
    }

    /**
     * Returns whether the input is the command that lists all tasks.
     */
    public boolean isListCommand(String input) {
        return input.equals(LIST_COMMAND);
    }

    /**
     * Returns whether the input starts with the command for marking a task as done.
     */
    public boolean isMarkCommand(String input) {
        return input.startsWith(MARK_COMMAND_PREFIX);
    }

    /**
     * Returns whether the input starts with the command for marking a task as not done.
     */
    public boolean isUnmarkCommand(String input) {
        return input.startsWith(UNMARK_COMMAND_PREFIX);
    }

    /**
     * Returns whether the input contains a delete command word.
     */
    public boolean isDeleteCommand(String input) {
        return DELETE_WORD_PATTERN.matcher(input).find();
    }

    /**
     * Returns whether the input is the command that starts a find search.
     */
    public boolean isFindCommand(String input) {
        return input.equals(FIND_COMMAND);
    }

    /**
     * Returns the task number from a mark command.
     * If the command does not contain a number, -1 is returned.
     */
    public int parseMarkNumber(String input) {
        return parseTaskNumber(input.substring(MARK_COMMAND_PREFIX.length()).trim());
    }

    /**
     * Returns the task number from an unmark command.
     * If the command does not contain a number, -1 is returned.
     */
    public int parseUnmarkNumber(String input) {
        return parseTaskNumber(input.substring(UNMARK_COMMAND_PREFIX.length()).trim());
    }

    /**
     * Returns the task number from a delete command embedded in the input.
     *
     * @throws NonNumberDeleteException If the delete command is missing a number or uses a non-numeric value.
     */
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

    /**
     * Returns a task parsed from the user's input.
     * If a previous command is waiting for a time confirmation, the input is interpreted as that time answer.
     *
     * @throws LarperException If the input does not match a valid task command or pending time answer.
     */
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

        if (isTaskTypeWithoutDescription(input)) {
            throw new NoDescriptionException();
        }

        if (input.startsWith(TODO_COMMAND_PREFIX)) {
            return parseTodo(input);
        }
        if (input.startsWith(DEADLINE_COMMAND_PREFIX)) {
            return parseDeadline(input);
        }
        if (input.startsWith(EVENT_COMMAND_PREFIX)) {
            return parseEvent(input);
        }

        throw new NoTaskTypeException();
    }

    private Task parseTodo(String input) throws NoDescriptionException {
        String description = input.substring(TODO_COMMAND_PREFIX.length()).trim();
        if (description.isEmpty() || countSlashes(description) != 0) {
            throw new NoDescriptionException();
        }
        return new Todo(description);
    }

    private Task parseDeadline(String input) throws LarperException {
        String taskInfo = input.substring(DEADLINE_COMMAND_PREFIX.length()).trim();
        int byIndex = taskInfo.indexOf(BY_MARKER);
        if (byIndex == -1) {
            throw new InvalidDateException(PENDING_DEADLINE);
        }

        String description = taskInfo.substring(0, byIndex).trim();
        String by = taskInfo.substring(byIndex + BY_MARKER.length()).trim();
        ensureDescriptionPresent(description);

        TaskDateTime byDateTime = parseTaskDateTime(by, PENDING_DEADLINE, PENDING_DEADLINE,
                description, null, null);
        return new Deadline(description, byDateTime.getDate(), byDateTime.getTime());
    }

    private Task parseEvent(String input) throws LarperException {
        String taskInfo = input.substring(EVENT_COMMAND_PREFIX.length()).trim();
        int fromIndex = taskInfo.indexOf(FROM_MARKER);
        int toIndex = taskInfo.indexOf(TO_MARKER);
        if (fromIndex == -1 || toIndex == -1 || fromIndex > toIndex) {
            throw new InvalidDateException("event start or end");
        }

        String description = taskInfo.substring(0, fromIndex).trim();
        String from = taskInfo.substring(fromIndex + FROM_MARKER.length(), toIndex).trim();
        String to = taskInfo.substring(toIndex + TO_MARKER.length()).trim();
        ensureDescriptionPresent(description);
        ensureEventHasExpectedMarkers(taskInfo);

        TaskDateTime startDateTime = parseTaskDateTime(from, "event start", PENDING_EVENT_START,
                description, to, null);
        TaskDateTime endDateTime = parseTaskDateTime(to, "event end", PENDING_EVENT_END,
                description, startDateTime.getDate().toString(), startDateTime.getTime());
        return new Event(description, startDateTime, endDateTime);
    }

    private Task completePendingTask(String input) throws LarperException {
        String time = input.trim();
        if (time.isEmpty() || !isValidTimeAnswer(time)) {
            throw new InvalidTimeException(pendingTask.waitingFor);
        }

        PendingTask taskToComplete = pendingTask;
        pendingTask = null;

        if (taskToComplete.type.equals(PENDING_DEADLINE)) {
            String normalizedTime = TaskDateTimeParser.normalizeTime(time);
            return new Deadline(taskToComplete.description, LocalDate.parse(taskToComplete.firstDate), normalizedTime);
        }

        if (taskToComplete.type.equals(PENDING_EVENT_START)) {
            String normalizedTime = TaskDateTimeParser.normalizeTime(time);
            TaskDateTime endDateTime = parseTaskDateTime(taskToComplete.secondDate, "event end", PENDING_EVENT_END,
                    taskToComplete.description, taskToComplete.firstDate, normalizedTime);
            return new Event(taskToComplete.description, LocalDate.parse(taskToComplete.firstDate), normalizedTime,
                    endDateTime.getDate(), endDateTime.getTime());
        }

        if (taskToComplete.type.equals(PENDING_EVENT_END)) {
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
        return input.startsWith(TODO_COMMAND_PREFIX) || input.startsWith(DEADLINE_COMMAND_PREFIX)
                || input.startsWith(EVENT_COMMAND_PREFIX) || isTaskTypeWithoutDescription(input);
    }

    private boolean isTaskTypeWithoutDescription(String input) {
        return input.equals(TODO_COMMAND) || input.equals(DEADLINE_COMMAND) || input.equals(EVENT_COMMAND);
    }

    private void ensureDescriptionPresent(String description) throws NoDescriptionException {
        if (description.isEmpty()) {
            throw new NoDescriptionException();
        }
    }

    private void ensureEventHasExpectedMarkers(String taskInfo) throws InvalidDateException {
        if (countSlashes(taskInfo) != EXPECTED_EVENT_SLASH_COUNT) {
            throw new InvalidDateException("event start or end");
        }
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
