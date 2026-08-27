package larper.ui;

import java.util.ArrayList;
import java.util.Scanner;

import larper.task.FindResult;
import larper.task.Task;
import larper.task.TaskList;

/**
 * Handles all console input and output for Larper.
 */
public class Ui {
    private static final String LINE = "_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_";
    private static final String BANNER = " _\n"
            + "| |       __ _   _ __   _ __     ___   _ __\n"
            + "| |      / _` | | '__| | '_ \\   / _ \\ | '__|\n"
            + "| |___  | (_| | | |    | |_) | |  __/ | |\n"
            + "|_____|  \\__,_| |_|    | .__/   \\___| |_|\n"
            + "                       |_|\n";

    private Scanner scanner;

    /**
     * Creates a console user interface that reads from standard input.
     */
    public Ui() {
        scanner = new Scanner(System.in);
    }

    /**
     * Returns whether the console has another line of user input.
     */
    public boolean hasNextInput() {
        return scanner.hasNextLine();
    }

    /**
     * Returns the next line entered by the user.
     */
    public String readInput() {
        return scanner.nextLine();
    }

    /**
     * Shows Larper's greeting and command prompt.
     */
    public void showWelcome() {
        System.out.println(LINE);
        System.out.print(BANNER);
        System.out.println("Fine day! I'm Larper.");
        System.out.println();
        System.out.println(" What can I do for you?");
        System.out.println();
        System.out.println(LINE);
    }

    /**
     * Shows the separator line used between console messages.
     */
    public void showLine() {
        System.out.println(LINE);
    }

    /**
     * Shows Larper's exit message.
     */
    public void showExit() {
        System.out.println(" Bye. Hope to see you again soon!");
        showLine();
    }

    /**
     * Shows all tasks in their current order.
     */
    public void showTaskList(TaskList tasks) {
        int count = 0;
        System.out.println(" Here are the tasks in your list:");
        while (count < tasks.size()) {
            System.out.println(" " + (count + 1) + ". " + tasks.getTask(count + 1));
            count++;
        }
        showLine();
    }

    /**
     * Shows the prompt that asks what phrase to find.
     */
    public void showFindPrompt() {
        System.out.println(" What do you want me to find?");
        showLine();
    }

    /**
     * Shows tasks found by the latest find search.
     */
    public void showFindResults(ArrayList<FindResult> results) {
        int index = 0;
        System.out.println(" OK found it!!!");
        while (index < results.size()) {
            System.out.println(" " + results.get(index));
            index++;
        }
        showLine();
    }

    /**
     * Shows the message for a mark command without a valid task number.
     */
    public void showInvalidMarkNumber() {
        System.out.println(" Please give me a valid task number to mark.");
    }

    /**
     * Shows the message for an unmark command without a valid task number.
     */
    public void showInvalidUnmarkNumber() {
        System.out.println(" Please give me a valid task number to unmark.");
    }

    /**
     * Shows the message for a command that refers to a task number not in the list.
     */
    public void showMissingTaskNumber() {
        System.out.println(" That task number does not exist.");
    }

    /**
     * Shows the task that has just been marked as done.
     */
    public void showMarkedTask(Task task) {
        System.out.println(" Nice! I've marked this task as done:");
        System.out.println(" " + task);
    }

    /**
     * Shows the task that has just been marked as not done.
     */
    public void showUnmarkedTask(Task task) {
        System.out.println(" OK, I've marked this task as not done yet:");
        System.out.println(" " + task);
    }

    /**
     * Shows the task that has just been deleted and the new task count.
     */
    public void showDeletedTask(Task task, int taskCount) {
        System.out.println(" Poof it gone now:");
        System.out.println(" " + task);
        showTaskCount(taskCount);
        showLine();
    }

    /**
     * Shows the task that has just been added and the new task count.
     */
    public void showAddedTask(Task task, int taskCount) {
        System.out.println(" Got it. I've added this task:");
        System.out.println(" " + task);
        showTaskCount(taskCount);
        showLine();
    }

    /**
     * Shows an error message followed by a separator line.
     */
    public void showError(String message) {
        System.out.println(message);
        showLine();
    }

    /**
     * Closes the scanner used to read console input.
     */
    public void close() {
        scanner.close();
    }

    private void showTaskCount(int taskCount) {
        String taskWord = taskCount == 1 ? "task" : "tasks";
        System.out.println(" Now you have " + taskCount + " " + taskWord + " in the list.");
    }
}
