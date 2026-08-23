import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

public class Larper {

    public static class Task {
        private String description;
        private boolean isDone;

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

        public void markAsDone() {
            isDone = true;
        }

        public void unmarkAsDone() {
            isDone = false;
        }

        public String getStatusIcon() {
            return isDone ? "X" : " ";
        }

        public String getTypeIcon() {
            return "?";
        }

        @Override
        public String toString() {
            return "[" + getTypeIcon() + "][" + getStatusIcon() + "] " + description;
        }
    }

    public static class Todo extends Task {
        public Todo(String description) {
            super(description);
        }

        @Override
        public String getTypeIcon() {
            return "T";
        }
    }

    public static class Deadline extends Task {
        private String byDate;
        private String byTime;

        public Deadline(String description, String byDate, String byTime) {
            super(description);
            this.byDate = byDate;
            this.byTime = byTime;
        }

        @Override
        public String getTypeIcon() {
            return "D";
        }

        @Override
        public String toString() {
            return super.toString() + " (by: " + byDate + " " + byTime + ")";
        }
    }

    public static class Event extends Task {
        private String startDate;
        private String startTime;
        private String endDate;
        private String endTime;

        public Event(String description, String startDate, String startTime, String endDate, String endTime) {
            super(description);
            this.startDate = startDate;
            this.startTime = startTime;
            this.endDate = endDate;
            this.endTime = endTime;
        }

        @Override
        public String getTypeIcon() {
            return "E";
        }

        @Override
        public String toString() {
            return super.toString() + " (from: " + startDate + " " + startTime
                    + " to: " + endDate + " " + endTime + ")";
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

    private static PendingTask pendingTask;

    public static void main(String[] args) {
        Task[] tasks = new Task[100];
        String line = "_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_";

        System.out.println(line);
        String banner = " _                              \n"
                + "| |       __ _   _ __   _ __     ___   _ __\n"
                + "| |      / _` | | '__| | '_ \\   / _ \\ | '__|\n"
                + "| |___  | (_| | | |    | |_) | |  __/ | |\n"
                + "|_____|  \\__,_| |_|    | .__/   \\___| |_|\n"
                + "                       |_|\n";
        System.out.print(banner);
        System.out.println("Fine day! I'm Larper. \n");
        System.out.println(" What can I do for you? \n");
        System.out.println(line);

        Scanner scanner = new Scanner(System.in);
        int taskCount = 0;
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();

            System.out.println(line);

            if (input.equals("exit")) {
                System.out.println(" Bye. Hope to see you again soon!");
                System.out.println(line);
                break;
            }

            try {
                if (input.equals("list")) {
                    int count = 0;
                    System.out.println(" Here are the tasks in your list:");
                    while (count < taskCount) {
                        System.out.println(" " + (count + 1) + ". " + tasks[count]);
                        count++;
                    }
                    System.out.println(line);
                } else if (input.startsWith("mark ")) {
                    int number = parseTaskNumber(input.substring(5).trim());
                    if (number == -1) {
                        System.out.println(" Please give me a valid task number to mark.");
                    } else if (number < 1 || number > taskCount) {
                        System.out.println(" That task number does not exist.");
                    } else if (tasks[number - 1].isDone()) {
                        throw new MarkingException();
                    } else {
                        tasks[number - 1].markAsDone();
                        System.out.println(" Nice! I've marked this task as done:");
                        System.out.println(" " + tasks[number - 1]);
                    }
                    System.out.println(line);

                } else if (input.startsWith("unmark ")) {
                    int number = parseTaskNumber(input.substring(7).trim());
                    if (number == -1) {
                        System.out.println(" Please give me a valid task number to unmark.");
                    } else if (number < 1 || number > taskCount) {
                        System.out.println(" That task number does not exist.");
                    } else if (!tasks[number - 1].isDone()) {
                        throw new UnmarkingException();
                    } else {
                        tasks[number - 1].unmarkAsDone();
                        System.out.println(" OK, I've marked this task as not done yet:");
                        System.out.println(" " + tasks[number - 1]);
                    }
                    System.out.println(line);

                } else {
                    Task task;
                    if (pendingTask != null && !startsWithTaskCommand(input)) {
                        task = completePendingTask(input);
                    } else {
                        pendingTask = null;
                        task = createTask(input);
                    }
                    tasks[taskCount] = task;
                    taskCount++;
                    System.out.println(" Got it. I've added this task:");
                    System.out.println(" " + tasks[taskCount - 1]);
                    printTaskCount(taskCount);
                    System.out.println(line);
                }
            } catch (LarperException e) {
                System.out.println(e.getMessage());
                System.out.println(line);
            }

        }

        scanner.close();
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
            if (countSlashes(taskInfo) != 1) {
                throw new InvalidDateException("deadline");
            }
            String[] byDateTime = parseDateTime(by, "deadline", "deadline", description, null, null);
            return new Deadline(description, byDateTime[0], byDateTime[1]);
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
            String[] startDateTime = parseDateTime(from, "event start", "event-start", description, to, null);
            String[] endDateTime = parseDateTime(to, "event end", "event-end", description,
                    startDateTime[0], startDateTime[1]);
            return new Event(description, startDateTime[0], startDateTime[1], endDateTime[0], endDateTime[1]);
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
            return new Deadline(taskToComplete.description, taskToComplete.firstDate, time);
        }

        if (taskToComplete.type.equals("event-start")) {
            String[] endDateTime = parseDateTime(taskToComplete.secondDate, "event end", "event-end",
                    taskToComplete.description, taskToComplete.firstDate, time);
            return new Event(taskToComplete.description, taskToComplete.firstDate, time,
                    endDateTime[0], endDateTime[1]);
        }

        if (taskToComplete.type.equals("event-end")) {
            return new Event(taskToComplete.description, taskToComplete.firstDate, taskToComplete.firstTime,
                    taskToComplete.secondDate, time);
        }

        throw new NoTaskTypeException();
    }

    private static String[] parseDateTime(String text, String label, String pendingType, String description,
            String extraDate, String extraTime) throws InvalidDateException, InvalidTimeException {
        String trimmedText = text.trim();
        if (trimmedText.isEmpty()) {
            throw new InvalidDateException(label);
        }

        if (isNoTimeOnly(trimmedText)) {
            throw new InvalidDateException(label);
        }

        String date = trimmedText;
        String time = "";
        if (endsWithNoTime(trimmedText)) {
            date = trimmedText.substring(0, trimmedText.length() - " no time".length()).trim();
            time = "no time";
        } else {
            int lastSpaceIndex = trimmedText.lastIndexOf(' ');
            if (lastSpaceIndex != -1) {
                String possibleDate = trimmedText.substring(0, lastSpaceIndex).trim();
                String possibleTime = trimmedText.substring(lastSpaceIndex + 1).trim();
                if (looksLikeTime(possibleTime)) {
                    date = possibleDate;
                    time = possibleTime;
                }
            }
        }

        if (date.isEmpty() || looksLikeTime(date) || isNoTimeOnly(date) || !looksLikeDate(date)) {
            throw new InvalidDateException(label);
        }

        date = normalizeDate(date);

        if (time.isEmpty()) {
            if (pendingType.equals("event-end")) {
                pendingTask = new PendingTask(pendingType, description, extraDate, extraTime, date, label);
            } else {
                pendingTask = new PendingTask(pendingType, description, date, extraTime, extraDate, label);
            }
            throw new InvalidTimeException(label);
        }

        return new String[] { date, time };
    }

    private static boolean looksLikeTime(String text) {
        String lowerText = text.toLowerCase();
        return lowerText.contains("am") || lowerText.contains("pm");
    }

    private static boolean isValidTimeAnswer(String text) {
        return isNoTimeOnly(text) || looksLikeTime(text);
    }

    private static boolean looksLikeDate(String text) {
        String lowerText = text.toLowerCase().trim();
        return lowerText.matches(".*\\d{1,2}/\\d{1,2}.*") || containsMonth(lowerText)
                || parseDayOfWeek(lowerText) != null;
    }

    private static String normalizeDate(String text) {
        DayOfWeek dayOfWeek = parseDayOfWeek(text.toLowerCase().trim());
        if (dayOfWeek == null) {
            return text;
        }

        LocalDate today = getToday();
        int daysAhead = dayOfWeek.getValue() - today.getDayOfWeek().getValue();
        if (daysAhead < 0) {
            daysAhead += 7;
        }

        LocalDate date = today.plusDays(daysAhead);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM", Locale.ENGLISH);
        return date.format(formatter).toLowerCase();
    }

    private static LocalDate getToday() {
        String todayProperty = System.getProperty("larper.today");
        if (todayProperty != null && !todayProperty.isBlank()) {
            return LocalDate.parse(todayProperty);
        }
        return LocalDate.now();
    }

    private static DayOfWeek parseDayOfWeek(String text) {
        String trimmedText = text.trim();
        switch (trimmedText) {
        case "mon":
        case "monday":
            return DayOfWeek.MONDAY;
        case "tue":
        case "tues":
        case "tuesday":
            return DayOfWeek.TUESDAY;
        case "wed":
        case "wednesday":
            return DayOfWeek.WEDNESDAY;
        case "thu":
        case "thur":
        case "thurs":
        case "thursday":
            return DayOfWeek.THURSDAY;
        case "fri":
        case "friday":
            return DayOfWeek.FRIDAY;
        case "sat":
        case "saturday":
            return DayOfWeek.SATURDAY;
        case "sun":
        case "sunday":
            return DayOfWeek.SUNDAY;
        default:
            return null;
        }
    }

    private static boolean containsMonth(String text) {
        String[] months = {"jan", "january", "feb", "february", "mar", "march", "apr", "april",
                "may", "jun", "june", "jul", "july", "aug", "august", "sep", "sept", "september",
                "oct", "october", "nov", "november", "dec", "december"};

        String paddedText = " " + text + " ";
        int index = 0;
        while (index < months.length) {
            if (paddedText.contains(" " + months[index] + " ")) {
                return true;
            }
            index++;
        }
        return false;
    }

    private static boolean isNoTimeOnly(String text) {
        return text.trim().equalsIgnoreCase("no time");
    }

    private static boolean endsWithNoTime(String text) {
        return text.trim().toLowerCase().endsWith(" no time");
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

    private static void printTaskCount(int taskCount) {
        String taskWord = taskCount == 1 ? "task" : "tasks";
        System.out.println(" Now you have " + taskCount + " " + taskWord + " in the list.");
    }
}
