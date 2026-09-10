package larper.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        assert tasks != null : "TaskList should wrap an existing task collection.";
        this.tasks = tasks;
        taskCount = tasks.size();
        assertIsConsistent();
    }

    /**
     * Returns the number of tasks currently in the list.
     */
    public int size() {
        assertIsConsistent();
        return taskCount;
    }

    /**
     * Returns whether the task list has no tasks.
     */
    public boolean isEmpty() {
        assertIsConsistent();
        return taskCount == 0;
    }

    /**
     * Returns whether the specified one-based task number exists in the list.
     */
    public boolean hasTaskNumber(int number) {
        assertIsConsistent();
        return number >= 1 && number <= taskCount;
    }

    /**
     * Returns the task with the specified one-based task number.
     */
    public Task getTask(int number) {
        assert hasTaskNumber(number) : "Caller should validate task number before retrieving a task.";
        return tasks.get(number - 1);
    }

    public ArrayList<Task> getTasks() {
        assertIsConsistent();
        return tasks;
    }

    /**
     * Adds a task to the end of the list.
     *
     * @param task Task to add.
     */
    public void addTask(Task task) {
        assert task != null : "TaskList should not store null tasks.";
        assertIsConsistent();
        tasks.add(task);
        taskCount++;
        assertIsConsistent();
    }

    /**
     * Deletes and returns the task with the specified one-based task number.
     *
     * @throws EmptyDeletionException If the list is empty.
     * @throws InvalidNumberDeleteException If the task number is outside the list.
     */
    public Task deleteTask(int number) throws EmptyDeletionException, InvalidNumberDeleteException {
        assertIsConsistent();
        if (isEmpty()) {
            throw new EmptyDeletionException();
        }
        if (!hasTaskNumber(number)) {
            throw new InvalidNumberDeleteException(taskCount);
        }

        taskCount--;
        Task removedTask = tasks.remove(number - 1);
        assert removedTask != null : "Deleting a valid task number should return the removed task.";
        assertIsConsistent();
        return removedTask;
    }

    /**
     * Returns tasks with descriptions containing the phrase or an exact matching tag, ignoring case.
     *
     * @throws NoFindException If no task description or tag matches the phrase.
     */
    public ArrayList<FindResult> findTasks(String phrase) throws NoFindException {
        assert phrase != null : "Find phrase should be an empty string instead of null.";
        assertIsConsistent();
        String normalizedPhrase = normalizeFindPhrase(phrase);
        ArrayList<FindResult> results = IntStream.range(0, taskCount)
                .filter(index -> !normalizedPhrase.isEmpty()
                        && tasks.get(index).matchesSearch(normalizedPhrase))
                .mapToObj(index -> new FindResult(index + 1, tasks.get(index)))
                .collect(Collectors.toCollection(ArrayList::new));

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
        assert hasTaskNumber(number) : "Caller should validate task number before marking a task.";
        Task task = getTask(number);
        if (task.isDone()) {
            throw new MarkingException();
        }

        task.markAsDone();
        assert task.isDone() : "Task should be done after markAsDone is called.";
        return task;
    }

    /**
     * Unmarks and returns the task with the specified one-based task number.
     *
     * @throws UnmarkingException If the task is already not done.
     */
    public Task unmarkTask(int number) throws UnmarkingException {
        assert hasTaskNumber(number) : "Caller should validate task number before unmarking a task.";
        Task task = getTask(number);
        if (!task.isDone()) {
            throw new UnmarkingException();
        }

        task.unmarkAsDone();
        assert !task.isDone() : "Task should not be done after unmarkAsDone is called.";
        return task;
    }

    /**
     * Adds tags to the task with the specified one-based task number.
     *
     * @param number One-based task number.
     * @param tags Tags to add.
     * @return The updated task.
     */
    public Task tagTask(int number, Collection<String> tags) {
        assert hasTaskNumber(number) : "Caller should validate task number before tagging a task.";
        assert tags != null && !tags.isEmpty() : "Tagging should receive at least one tag.";
        Task task = getTask(number);
        task.addTags(tags);
        return task;
    }

    /**
     * Removes tags from the task with the specified one-based task number.
     *
     * @param number One-based task number.
     * @param tags Tags to remove.
     * @return The updated task.
     */
    public Task untagTask(int number, Collection<String> tags) {
        assert hasTaskNumber(number) : "Caller should validate task number before untagging a task.";
        assert tags != null && !tags.isEmpty() : "Untagging should receive at least one tag.";
        Task task = getTask(number);
        for (String tag : tags) {
            task.removeTag(tag);
        }
        return task;
    }

    /**
     * Returns tasks that contain the specified tag, ignoring tag case.
     *
     * @param tag Exact tag to find.
     * @return Matching tasks with their original task numbers.
     * @throws NoFindException If no task has the tag.
     */
    public ArrayList<FindResult> findTasksByTag(String tag) throws NoFindException {
        assert Task.isValidTag(Task.normalizeTag(tag)) : "Find tag should be validated before searching.";
        ArrayList<FindResult> results = new ArrayList<>();
        String normalizedTag = Task.normalizeTag(tag);
        int index = 0;
        while (index < taskCount) {
            Task task = tasks.get(index);
            if (task.hasTag(normalizedTag)) {
                results.add(new FindResult(index + 1, task));
            }
            index++;
        }

        if (results.isEmpty()) {
            throw new NoFindException();
        }
        return results;
    }

    private void assertIsConsistent() {
        assert taskCount == tasks.size() : "Cached task count should match the backing list size.";
    }

    private String normalizeFindPhrase(String phrase) {
        return phrase.trim().replaceAll("\\s+", " ").toLowerCase();
    }
}
