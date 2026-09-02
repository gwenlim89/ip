package larper;

import javafx.application.Application;

/**
 * Launches the JavaFX application through a regular Java main method.
 */
public class Launcher {
    /**
     * Starts the JavaFX runtime with the main application class.
     *
     * @param args Command-line arguments passed to JavaFX.
     */
    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}
