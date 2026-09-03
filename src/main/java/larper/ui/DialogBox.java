package larper.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * Displays one chat message with an avatar and message bubble.
 * User messages appear on the right, while Larper messages appear on the left.
 */
public class DialogBox extends HBox {
    private static final int AVATAR_SIZE = 54;
    private static final int MESSAGE_WIDTH = 460;
    private static final String BASE_MESSAGE_STYLE = "-fx-background-radius: 12;"
            + " -fx-border-radius: 12;"
            + " -fx-border-width: 1;"
            + " -fx-padding: 10;"
            + " -fx-font-family: 'Verdana';"
            + " -fx-font-size: 13;";
    private static final String LARPER_MESSAGE_STYLE = "-fx-background-color: #f1f3f5;"
            + " -fx-border-color: #d1d5db;"
            + BASE_MESSAGE_STYLE;
    private static final String USER_MESSAGE_STYLE = "-fx-background-color: #dbeafe;"
            + " -fx-border-color: #60a5fa;"
            + BASE_MESSAGE_STYLE;

    private final Label text;
    private final ImageView displayPicture;

    /**
     * Creates a chat row with a message and display picture.
     *
     * @param message Text to display in the chat bubble.
     * @param image Display picture to show beside the message.
     */
    public DialogBox(String message, Image image) {
        text = new Label(message);
        text.setWrapText(true);
        text.setMaxWidth(MESSAGE_WIDTH);
        text.setMinHeight(Region.USE_PREF_SIZE);

        displayPicture = new ImageView(image);
        displayPicture.setFitWidth(AVATAR_SIZE);
        displayPicture.setFitHeight(AVATAR_SIZE);
        displayPicture.setPreserveRatio(true);

        setSpacing(8);
        setPadding(new Insets(2, 0, 2, 0));
        setMaxWidth(Double.MAX_VALUE);
        getChildren().addAll(text, displayPicture);
    }

    /**
     * Returns a dialog box aligned for a user message.
     *
     * @param message Text entered by the user.
     * @param image User display picture.
     * @return Dialog box for the user message.
     */
    public static DialogBox getUserDialog(String message, Image image) {
        DialogBox dialogBox = new DialogBox(message, image);
        dialogBox.setAlignment(Pos.CENTER_RIGHT);
        dialogBox.text.setStyle(USER_MESSAGE_STYLE);
        return dialogBox;
    }

    /**
     * Returns a dialog box aligned for a Larper message.
     *
     * @param message Text returned by Larper.
     * @param image Larper display picture.
     * @return Dialog box for the Larper message.
     */
    public static DialogBox getLarperDialog(String message, Image image) {
        DialogBox dialogBox = new DialogBox(message, image);
        dialogBox.text.setStyle(LARPER_MESSAGE_STYLE);
        dialogBox.flip();
        return dialogBox;
    }

    /**
     * Reorders the display picture and text so the avatar appears on the left.
     */
    private void flip() {
        setAlignment(Pos.CENTER_LEFT);
        ObservableList<Node> nodes = FXCollections.observableArrayList(getChildren());
        FXCollections.reverse(nodes);
        getChildren().setAll(nodes);
    }
}
