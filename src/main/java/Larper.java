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
        private String by;

        public Deadline(String description, String by) {
            super(description);
            this.by = by;
        }

        @Override
        public String getTypeIcon() {
            return "D";
        }

        @Override
        public String toString() {
            return super.toString() + " (by: " + by + ")";
        }
    }

    public static class Event extends Task {
        private String from;
        private String to;

        public Event(String description, String from, String to) {
            super(description);
            this.from = from;
            this.to = to;
        }

        @Override
        public String getTypeIcon() {
            return "E";
        }

        @Override
        public String toString() {
            return super.toString() + " (from: " + from + " to: " + to + ")";
        }
    }

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
                } else {
                    tasks[number - 1].unmarkAsDone();
                    System.out.println(" OK, I've marked this task as not done yet:");
                    System.out.println(" " + tasks[number - 1]);
                }
                System.out.println(line);

            } else {
                Task task = createTask(input);
                if (task == null) {
                    System.out.println(" Please use one of these formats:");
                    System.out.println(" todo DESCRIPTION");
                    System.out.println(" deadline DESCRIPTION /by TIME");
                    System.out.println(" event DESCRIPTION /from START /to END");
                } else {
                    tasks[taskCount] = task;
                    taskCount++;
                    System.out.println(" Got it. I've added this task:");
                    System.out.println(" " + tasks[taskCount - 1]);
                    printTaskCount(taskCount);
                }
                System.out.println(line);
            }

        }

        scanner.close();
    }

    private static Task createTask(String input) {
        if (input.isEmpty()) {
            return null;
        }

        if (input.startsWith("todo ")) {
            String description = input.substring(5).trim();
            if (description.isEmpty() || countSlashes(description) != 0) {
                return null;
            }
            return new Todo(description);
        }

        if (input.startsWith("deadline ")) {
            String taskInfo = input.substring(9).trim();
            int byIndex = taskInfo.indexOf("/by");
            if (byIndex == -1) {
                return null;
            }
            String description = taskInfo.substring(0, byIndex).trim();
            String by = taskInfo.substring(byIndex + 3).trim();
            if (description.isEmpty() || by.isEmpty() || countSlashes(taskInfo) != 1) {
                return null;
            }
            return new Deadline(description, by);
        }

        if (input.startsWith("event ")) {
            String taskInfo = input.substring(6).trim();
            int fromIndex = taskInfo.indexOf("/from");
            int toIndex = taskInfo.indexOf("/to");
            if (fromIndex == -1 || toIndex == -1 || fromIndex > toIndex) {
                return null;
            }
            String description = taskInfo.substring(0, fromIndex).trim();
            String from = taskInfo.substring(fromIndex + 5, toIndex).trim();
            String to = taskInfo.substring(toIndex + 3).trim();
            if (description.isEmpty() || from.isEmpty() || to.isEmpty() || countSlashes(taskInfo) != 2) {
                return null;
            }
            return new Event(description, from, to);
        }

        return null;
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
