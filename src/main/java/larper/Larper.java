package larper;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import larper.command.Parser;
import larper.exception.LarperException;
import larper.storage.Storage;
import larper.task.FindResult;
import larper.task.Task;
import larper.task.TaskList;
import larper.ui.Ui;

/**
 * Coordinates Larper's user interface, command parser, task list, and storage.
 */
public class Larper {
    private static final String DATA_PATH_PROPERTY = "larper.data.path";
    private static final Path DEFAULT_DATA_PATH = Path.of("data", "larperdata.txt");

    private Ui ui;
    private Parser parser;
    private Storage storage;
    private TaskList tasks;
    private boolean isWaitingForFindPhrase;

    /**
     * Creates a Larper chatbot that stores tasks at the specified data path.
     *
     * @param dataPath Path of the task data file.
     */
    public Larper(Path dataPath) {
        ui = new Ui();
        parser = new Parser();
        storage = new Storage(dataPath);
        tasks = loadTasks();
    }

    /**
     * Runs the main input loop until the user exits or the input stream ends.
     */
    public void run() {
        ui.showWelcome();
        while (ui.hasNextInput()) {
            String input = ui.readInput();
            ui.showLine();
            LarperResponse response = getResponse(input);
            ui.showMessage(response.getMessage());
            if (response.isExit()) {
                break;
            }
        }

        ui.close();
    }

    /**
     * Returns Larper's welcome message for non-console user interfaces.
     */
    public String getWelcomeMessage() {
        return Ui.formatWelcomeMessage().stripTrailing();
    }

    /**
     * Returns Larper's response to one user command.
     *
     * @param input User command to handle.
     * @return Larper's response message and session status.
     */
    public LarperResponse getResponse(String input) {
        if (parser.isExitCommand(input)) {
            return new LarperResponse(Ui.formatExitMessage(), true);
        }

        try {
            return new LarperResponse(executeCommand(input), false);
        } catch (LarperException e) {
            isWaitingForFindPhrase = false;
            return new LarperResponse(e.getMessage(), false);
        }
    }

    private String executeCommand(String input) throws LarperException {
        if (isWaitingForFindPhrase) {
            ArrayList<FindResult> results = tasks.findTasks(input);
            isWaitingForFindPhrase = false;
            return Ui.formatFindResults(results);
        } else if (parser.isListCommand(input)) {
            return Ui.formatTaskList(tasks);
        } else if (parser.isFindCommand(input)) {
            isWaitingForFindPhrase = true;
            return Ui.formatFindPrompt();
        } else if (parser.isMarkCommand(input)) {
            return executeMarkCommand(input);
        } else if (parser.isUnmarkCommand(input)) {
            return executeUnmarkCommand(input);
        } else if (parser.isDeleteCommand(input)) {
            int number = parser.parseDeleteNumber(input);
            Task removedTask = tasks.deleteTask(number);
            saveTasks();
            return Ui.formatDeletedTask(removedTask, tasks.size());
        } else {
            Task task = parser.parseTask(input);
            tasks.addTask(task);
            saveTasks();
            return Ui.formatAddedTask(task, tasks.size());
        }
    }

    private String executeMarkCommand(String input) throws LarperException {
        int number = parser.parseMarkNumber(input);
        if (number == -1) {
            return Ui.formatInvalidMarkNumber();
        } else if (!tasks.hasTaskNumber(number)) {
            return Ui.formatMissingTaskNumber();
        }

        Task markedTask = tasks.markTask(number);
        saveTasks();
        return Ui.formatMarkedTask(markedTask);
    }

    private String executeUnmarkCommand(String input) throws LarperException {
        int number = parser.parseUnmarkNumber(input);
        if (number == -1) {
            return Ui.formatInvalidUnmarkNumber();
        } else if (!tasks.hasTaskNumber(number)) {
            return Ui.formatMissingTaskNumber();
        }

        Task unmarkedTask = tasks.unmarkTask(number);
        saveTasks();
        return Ui.formatUnmarkedTask(unmarkedTask);
    }

    /**
     * Starts Larper using the configured data path or the default local data file.
     *
     * @param args Command line arguments, which are currently unused.
     */
    public static void main(String[] args) {
        new Larper(getDataPath()).run();
    }

    /**
     * Returns the configured data path or the default local data file.
     */
    public static Path getDataPath() {
        String dataPath = System.getProperty(DATA_PATH_PROPERTY);
        if (dataPath == null || dataPath.isBlank()) {
            return DEFAULT_DATA_PATH;
        }
        return Path.of(dataPath);
    }

    private void saveTasks() throws LarperException {
        try {
            storage.saveTasks(tasks.getTasks());
        } catch (IOException e) {
            throw new LarperException(" Larper could not save the task list to the local data file.");
        }
    }

    private TaskList loadTasks() {
        try {
            return new TaskList(storage.loadTasks());
        } catch (IOException e) {
            return new TaskList();
        }
    }
}
