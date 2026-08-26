import java.time.LocalDate;
import java.io.IOException;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Larper {
    private static final Pattern DELETE_PATTERN = Pattern.compile("\\bdelete\\b\\s+(\\S+)");
    private static final Pattern DELETE_WORD_PATTERN = Pattern.compile("\\bdelete\\b");
    private static final String DATA_PATH_PROPERTY = "larper.data.path";
    private static final Path DEFAULT_DATA_PATH = Path.of("data", "larperdata.txt");

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

    private static PendingTask pendingTask;

    public static void main(String[] args) {
        Ui ui = new Ui();
        Storage storage = new Storage(getDataPath());
        TaskList tasks = loadTasks(storage);

        ui.showWelcome();
        while (ui.hasNextInput()) {
            String input = ui.readInput();

            ui.showLine();

            if (input.equals("exit")) {
                ui.showExit();
                break;
            }

            try {
                if (input.equals("list")) {
                    ui.showTaskList(tasks);
                } else if (input.startsWith("mark ")) {
                    int number = parseTaskNumber(input.substring(5).trim());
                    if (number == -1) {
                        ui.showInvalidMarkNumber();
                    } else if (!tasks.hasTaskNumber(number)) {
                        ui.showMissingTaskNumber();
                    } else {
                        Task markedTask = tasks.markTask(number);
                        saveTasks(storage, tasks);
                        ui.showMarkedTask(markedTask);
                    }
                    ui.showLine();

                } else if (input.startsWith("unmark ")) {
                    int number = parseTaskNumber(input.substring(7).trim());
                    if (number == -1) {
                        ui.showInvalidUnmarkNumber();
                    } else if (!tasks.hasTaskNumber(number)) {
                        ui.showMissingTaskNumber();
                    } else {
                        Task unmarkedTask = tasks.unmarkTask(number);
                        saveTasks(storage, tasks);
                        ui.showUnmarkedTask(unmarkedTask);
                    }
                    ui.showLine();

                } else if (hasDeleteWord(input)) {
                    int number = parseDeleteNumber(input);
                    Task removedTask = tasks.deleteTask(number);
                    saveTasks(storage, tasks);
                    ui.showDeletedTask(removedTask, tasks.size());

                } else {
                    Task task;
                    if (pendingTask != null && !startsWithTaskCommand(input)) {
                        task = completePendingTask(input);
                    } else {
                        pendingTask = null;
                        task = createTask(input);
                    }
                    tasks.addTask(task);
                    saveTasks(storage, tasks);
                    ui.showAddedTask(task, tasks.size());
                }
            } catch (LarperException e) {
                ui.showError(e.getMessage());
            }

        }

        ui.close();
    }

    private static Task createTask(String input) throws LarperException {
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

    private static Task completePendingTask(String input) throws LarperException {
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

    private static TaskDateTime parseTaskDateTime(String text, String label, String pendingType, String description,
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

    private static boolean looksLikeTime(String text) {
        return TaskDateTimeParser.looksLikeTime(text);
    }

    private static boolean isValidTimeAnswer(String text) {
        return TaskDateTimeParser.isValidTimeAnswer(text);
    }

    private static boolean startsWithTaskCommand(String input) {
        return input.startsWith("todo ") || input.startsWith("deadline ") || input.startsWith("event ")
                || input.equals("todo") || input.equals("deadline") || input.equals("event");
    }

    private static int countSlashes(String input) {
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

    private static int parseTaskNumber(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static boolean hasDeleteWord(String input) {
        return DELETE_WORD_PATTERN.matcher(input).find();
    }

    private static int parseDeleteNumber(String input) throws NonNumberDeleteException {
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

    private static Path getDataPath() {
        String dataPath = System.getProperty(DATA_PATH_PROPERTY);
        if (dataPath == null || dataPath.isBlank()) {
            return DEFAULT_DATA_PATH;
        }
        return Path.of(dataPath);
    }

    private static void saveTasks(Storage storage, TaskList tasks) throws LarperException {
        try {
            storage.saveTasks(tasks.getTasks());
        } catch (IOException e) {
            throw new LarperException(" Larper could not save the task list to the local data file.");
        }
    }

    private static TaskList loadTasks(Storage storage) {
        try {
            return new TaskList(storage.loadTasks());
        } catch (IOException e) {
            return new TaskList();
        }
    }
}
