package larper.task;

import java.util.ArrayList;

import larper.exception.EmptyDeletionException;
import larper.exception.InvalidNumberDeleteException;
import larper.exception.MarkingException;
import larper.exception.UnmarkingException;

/**
 * Stores the tasks and provides operations for changing the task list.
 */
public class TaskList {
    private ArrayList<Task> tasks;
    private int taskCount;

    public TaskList() {
        this(new ArrayList<>());
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
        taskCount = tasks.size();
    }

    public int size() {
        return taskCount;
    }

    public boolean isEmpty() {
        return taskCount == 0;
    }

    public boolean hasTaskNumber(int number) {
        return number >= 1 && number <= taskCount;
    }

    public Task getTask(int number) {
        return tasks.get(number - 1);
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
        taskCount++;
    }

    public Task deleteTask(int number) throws EmptyDeletionException, InvalidNumberDeleteException {
        if (isEmpty()) {
            throw new EmptyDeletionException();
        }
        if (!hasTaskNumber(number)) {
            throw new InvalidNumberDeleteException(taskCount);
        }

        taskCount--;
        return tasks.remove(number - 1);
    }

    public Task markTask(int number) throws MarkingException {
        Task task = getTask(number);
        if (task.isDone()) {
            throw new MarkingException();
        }

        task.markAsDone();
        return task;
    }

    public Task unmarkTask(int number) throws UnmarkingException {
        Task task = getTask(number);
        if (!task.isDone()) {
            throw new UnmarkingException();
        }

        task.unmarkAsDone();
        return task;
    }
}
