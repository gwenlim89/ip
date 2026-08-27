package larper.ui;

import java.util.Scanner;

import larper.task.Task;
import larper.task.TaskList;

/**
 * Handles all console input and output for Larper.
 */
public class Ui {
    private static final String LINE = "_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_";
    private static final String BANNER = " _                              \n"
            + "| |       __ _   _ __   _ __     ___   _ __\n"
            + "| |      / _` | | '__| | '_ \\   / _ \\ | '__|\n"
            + "| |___  | (_| | | |    | |_) | |  __/ | |\n"
            + "|_____|  \\__,_| |_|    | .__/   \\___| |_|\n"
            + "                       |_|\n";

    private Scanner scanner;

    public Ui() {
        scanner = new Scanner(System.in);
    }

    public boolean hasNextInput() {
        return scanner.hasNextLine();
    }

    public String readInput() {
        return scanner.nextLine();
    }

    public void showWelcome() {
        System.out.println(LINE);
        System.out.print(BANNER);
        System.out.println("Fine day! I'm Larper. \n");
        System.out.println(" What can I do for you? \n");
        System.out.println(LINE);
    }

    public void showLine() {
        System.out.println(LINE);
    }

    public void showExit() {
        System.out.println(" Bye. Hope to see you again soon!");
        showLine();
    }

    public void showTaskList(TaskList tasks) {
        int count = 0;
        System.out.println(" Here are the tasks in your list:");
        while (count < tasks.size()) {
            System.out.println(" " + (count + 1) + ". " + tasks.getTask(count + 1));
            count++;
        }
        showLine();
    }

    public void showInvalidMarkNumber() {
        System.out.println(" Please give me a valid task number to mark.");
    }

    public void showInvalidUnmarkNumber() {
        System.out.println(" Please give me a valid task number to unmark.");
    }

    public void showMissingTaskNumber() {
        System.out.println(" That task number does not exist.");
    }

    public void showMarkedTask(Task task) {
        System.out.println(" Nice! I've marked this task as done:");
        System.out.println(" " + task);
    }

    public void showUnmarkedTask(Task task) {
        System.out.println(" OK, I've marked this task as not done yet:");
        System.out.println(" " + task);
    }

    public void showDeletedTask(Task task, int taskCount) {
        System.out.println(" Poof it gone now:");
        System.out.println(" " + task);
        showTaskCount(taskCount);
        showLine();
    }

    public void showAddedTask(Task task, int taskCount) {
        System.out.println(" Got it. I've added this task:");
        System.out.println(" " + task);
        showTaskCount(taskCount);
        showLine();
    }

    public void showError(String message) {
        System.out.println(message);
        showLine();
    }

    public void close() {
        scanner.close();
    }

    private void showTaskCount(int taskCount) {
        String taskWord = taskCount == 1 ? "task" : "tasks";
        System.out.println(" Now you have " + taskCount + " " + taskWord + " in the list.");
    }
}
