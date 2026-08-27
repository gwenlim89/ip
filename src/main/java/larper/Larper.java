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

            if (parser.isExitCommand(input)) {
                ui.showExit();
                break;
            }

            try {
                if (isWaitingForFindPhrase) {
                    ArrayList<FindResult> results = tasks.findTasks(input);
                    isWaitingForFindPhrase = false;
                    ui.showFindResults(results);
                } else if (parser.isListCommand(input)) {
                    ui.showTaskList(tasks);
                } else if (parser.isFindCommand(input)) {
                    isWaitingForFindPhrase = true;
                    ui.showFindPrompt();
                } else if (parser.isMarkCommand(input)) {
                    int number = parser.parseMarkNumber(input);
                    if (number == -1) {
                        ui.showInvalidMarkNumber();
                    } else if (!tasks.hasTaskNumber(number)) {
                        ui.showMissingTaskNumber();
                    } else {
                        Task markedTask = tasks.markTask(number);
                        saveTasks();
                        ui.showMarkedTask(markedTask);
                    }
                    ui.showLine();

                } else if (parser.isUnmarkCommand(input)) {
                    int number = parser.parseUnmarkNumber(input);
                    if (number == -1) {
                        ui.showInvalidUnmarkNumber();
                    } else if (!tasks.hasTaskNumber(number)) {
                        ui.showMissingTaskNumber();
                    } else {
                        Task unmarkedTask = tasks.unmarkTask(number);
                        saveTasks();
                        ui.showUnmarkedTask(unmarkedTask);
                    }
                    ui.showLine();

                } else if (parser.isDeleteCommand(input)) {
                    int number = parser.parseDeleteNumber(input);
                    Task removedTask = tasks.deleteTask(number);
                    saveTasks();
                    ui.showDeletedTask(removedTask, tasks.size());

                } else {
                    Task task = parser.parseTask(input);
                    tasks.addTask(task);
                    saveTasks();
                    ui.showAddedTask(task, tasks.size());
                }
            } catch (LarperException e) {
                isWaitingForFindPhrase = false;
                ui.showError(e.getMessage());
            }

        }

        ui.close();
    }

    /**
     * Starts Larper using the configured data path or the default local data file.
     *
     * @param args Command line arguments, which are currently unused.
     */
    public static void main(String[] args) {
        new Larper(getDataPath()).run();
    }

    private static Path getDataPath() {
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
