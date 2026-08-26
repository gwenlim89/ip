import java.io.IOException;
import java.nio.file.Path;

public class Larper {
    private static final String DATA_PATH_PROPERTY = "larper.data.path";
    private static final Path DEFAULT_DATA_PATH = Path.of("data", "larperdata.txt");

    public static void main(String[] args) {
        Ui ui = new Ui();
        Parser parser = new Parser();
        Storage storage = new Storage(getDataPath());
        TaskList tasks = loadTasks(storage);

        ui.showWelcome();
        while (ui.hasNextInput()) {
            String input = ui.readInput();

            ui.showLine();

            if (parser.isExitCommand(input)) {
                ui.showExit();
                break;
            }

            try {
                if (parser.isListCommand(input)) {
                    ui.showTaskList(tasks);
                } else if (parser.isMarkCommand(input)) {
                    int number = parser.parseMarkNumber(input);
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

                } else if (parser.isUnmarkCommand(input)) {
                    int number = parser.parseUnmarkNumber(input);
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

                } else if (parser.isDeleteCommand(input)) {
                    int number = parser.parseDeleteNumber(input);
                    Task removedTask = tasks.deleteTask(number);
                    saveTasks(storage, tasks);
                    ui.showDeletedTask(removedTask, tasks.size());

                } else {
                    Task task = parser.parseTask(input);
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
