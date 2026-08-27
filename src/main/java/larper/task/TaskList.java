package larper.task;

import java.util.ArrayList;

import larper.exception.EmptyDeletionException;
import larper.exception.InvalidNumberDeleteException;
import larper.exception.MarkingException;
import larper.exception.NoFindException;
import larper.exception.UnmarkingException;

/**
 * Stores the tasks and provides operations for changing the task list.
 */
public class TaskList {
    private ArrayList<Task> tasks;
    private int taskCount;

    /**
     * Creates an empty task list.
     */
    public TaskList() {
        this(new ArrayList<>());
    }

    /**
     * Creates a task list from existing tasks, such as tasks loaded from storage.
     *
     * @param tasks Existing tasks to manage.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
        taskCount = tasks.size();
    }

    /**
     * Returns the number of tasks currently in the list.
     */
    public int size() {
        return taskCount;
    }

    /**
     * Returns whether the task list has no tasks.
     */
    public boolean isEmpty() {
        return taskCount == 0;
    }

    /**
     * Returns whether the specified one-based task number exists in the list.
     */
    public boolean hasTaskNumber(int number) {
        return number >= 1 && number <= taskCount;
    }

    /**
     * Returns the task with the specified one-based task number.
     */
    public Task getTask(int number) {
        return tasks.get(number - 1);
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Adds a task to the end of the list.
     *
     * @param task Task to add.
     */
    public void addTask(Task task) {
        tasks.add(task);
        taskCount++;
    }

    /**
     * Deletes and returns the task with the specified one-based task number.
     *
     * @throws EmptyDeletionException If the list is empty.
     * @throws InvalidNumberDeleteException If the task number is outside the list.
     */
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

    /**
     * Returns tasks with descriptions containing the given phrase, ignoring case.
     *
     * @throws NoFindException If no task description contains the phrase.
     */
    public ArrayList<FindResult> findTasks(String phrase) throws NoFindException {
        String normalizedPhrase = normalizeFindPhrase(phrase);
        ArrayList<FindResult> results = new ArrayList<>();
        int index = 0;

        while (index < taskCount) {
            Task task = tasks.get(index);
            String normalizedDescription = normalizeFindPhrase(task.getDescription());
            if (!normalizedPhrase.isEmpty() && normalizedDescription.contains(normalizedPhrase)) {
                results.add(new FindResult(index + 1, task));
            }
            index++;
        }

        if (results.isEmpty()) {
            throw new NoFindException();
        }

        return results;
    }

    /**
     * Marks and returns the task with the specified one-based task number.
     *
     * @throws MarkingException If the task is already marked as done.
     */
    public Task markTask(int number) throws MarkingException {
        Task task = getTask(number);
        if (task.isDone()) {
            throw new MarkingException();
        }

        task.markAsDone();
        return task;
    }

    /**
     * Unmarks and returns the task with the specified one-based task number.
     *
     * @throws UnmarkingException If the task is already not done.
     */
    public Task unmarkTask(int number) throws UnmarkingException {
        Task task = getTask(number);
        if (!task.isDone()) {
            throw new UnmarkingException();
        }

        task.unmarkAsDone();
        return task;
    }

    private String normalizeFindPhrase(String phrase) {
        return phrase.trim().replaceAll("\\s+", " ").toLowerCase();
    }
}
