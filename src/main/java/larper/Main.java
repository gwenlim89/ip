package larper;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import larper.ui.DialogBox;

/**
 * Shows the JavaFX chat window for the Larper application.
 */
public class Main extends Application {
    private static final int WINDOW_WIDTH = 640;
    private static final int WINDOW_HEIGHT = 720;
    private static final int INPUT_HEIGHT = 52;
    private static final int CONTROL_HEIGHT = 32;
    private static final int SEND_BUTTON_WIDTH = 96;

    private Larper larper;
    private VBox dialogContainer;
    private ScrollPane scrollPane;
    private CheckBox autoScrollToggle;
    private TextField userInput;
    private Button sendButton;
    private Image larperImage;
    private Image userImage;

    /**
     * Starts the JavaFX application with the Larper chat interface.
     *
     * @param stage Primary stage supplied by JavaFX.
     */
    @Override
    public void start(Stage stage) {
        larper = new Larper(Larper.getDataPath());
        larperImage = loadImage("/images/larper.png");
        userImage = loadImage("/images/user.png");

        dialogContainer = new VBox(10);
        dialogContainer.setPadding(new Insets(12));
        dialogContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);

        scrollPane = new ScrollPane(dialogContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        dialogContainer.heightProperty().addListener((observable, oldValue, newValue) -> scrollToLatestMessage());

        autoScrollToggle = new CheckBox("Auto-scroll");
        autoScrollToggle.setSelected(true);
        autoScrollToggle.setPadding(new Insets(0, 12, 0, 12));
        autoScrollToggle.setOnAction(event -> scrollToLatestMessage());

        userInput = new TextField();
        userInput.setPromptText("Type a command...");
        userInput.setOnAction(event -> handleUserInput());
        userInput.setPrefHeight(INPUT_HEIGHT);

        sendButton = new Button("Send");
        sendButton.setOnAction(event -> handleUserInput());
        sendButton.setPrefWidth(SEND_BUTTON_WIDTH);
        sendButton.setPrefHeight(INPUT_HEIGHT);

        AnchorPane root = new AnchorPane();
        root.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        root.getChildren().addAll(scrollPane, autoScrollToggle, userInput, sendButton);
        anchorControls();

        addDialog(larper.getWelcomeMessage(), false);

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        stage.setTitle("Larper");
        stage.setResizable(false);
        stage.setMinHeight(WINDOW_HEIGHT);
        stage.setMinWidth(WINDOW_WIDTH);
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.centerOnScreen();
        stage.show();
        stage.toFront();
        stage.requestFocus();
        Platform.runLater(() -> stage.setAlwaysOnTop(false));
    }

    private void handleUserInput() {
        String input = userInput.getText();
        if (input.isBlank()) {
            return;
        }

        addDialog(input, true);
        userInput.clear();

        LarperResponse response = larper.getResponse(input);
        addDialog(response.getMessage(), false);
        if (response.isExit()) {
            userInput.setDisable(true);
            sendButton.setDisable(true);
        }
    }

    private void addDialog(String message, boolean isUser) {
        DialogBox dialogBox = isUser
                ? DialogBox.getUserDialog(message, userImage)
                : DialogBox.getLarperDialog(message, larperImage);
        dialogContainer.getChildren().add(dialogBox);
    }

    private void anchorControls() {
        AnchorPane.setTopAnchor(scrollPane, 0.0);
        AnchorPane.setRightAnchor(scrollPane, 0.0);
        AnchorPane.setBottomAnchor(scrollPane, (double) INPUT_HEIGHT + CONTROL_HEIGHT);
        AnchorPane.setLeftAnchor(scrollPane, 0.0);

        AnchorPane.setRightAnchor(autoScrollToggle, 0.0);
        AnchorPane.setBottomAnchor(autoScrollToggle, (double) INPUT_HEIGHT);
        AnchorPane.setLeftAnchor(autoScrollToggle, 0.0);

        AnchorPane.setRightAnchor(userInput, (double) SEND_BUTTON_WIDTH);
        AnchorPane.setBottomAnchor(userInput, 0.0);
        AnchorPane.setLeftAnchor(userInput, 0.0);

        AnchorPane.setRightAnchor(sendButton, 0.0);
        AnchorPane.setBottomAnchor(sendButton, 0.0);
    }

    private void scrollToLatestMessage() {
        if (autoScrollToggle != null && autoScrollToggle.isSelected()) {
            scrollPane.setVvalue(1.0);
        }
    }

    private Image loadImage(String imagePath) {
        return new Image(Main.class.getResourceAsStream(imagePath));
    }
}
