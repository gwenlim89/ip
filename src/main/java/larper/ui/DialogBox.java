package larper.ui;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * Displays one chat message with an avatar and message bubble.
 * User messages appear on the right, while Larper messages appear on the left.
 */
public class DialogBox extends HBox {
    private static final int AVATAR_SIZE = 54;
    private static final int MESSAGE_WIDTH = 460;
    private static final Pattern TAG_PATTERN = Pattern.compile("\\[#([A-Za-z0-9]+)\\]");
    private static final String[] TAG_COLORS = {
        "#fbcfe8", "#bfdbfe", "#bbf7d0", "#fde68a", "#ddd6fe", "#fed7aa"
    };
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

    private final VBox text;
    private final ImageView displayPicture;

    /**
     * Creates a chat row with a message and display picture.
     *
     * @param message Text to display in the chat bubble.
     * @param image Display picture to show beside the message.
     */
    public DialogBox(String message, Image image) {
        assert message != null : "Dialog box message should not be null.";
        assert image != null : "Dialog box image should not be null.";
        text = createMessageBubble(message);
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
     * Returns a message bubble with valid tag patterns rendered as chips.
     *
     * @param message Message text to split into normal text and tag chips.
     * @return Flow pane containing the styled message content.
     */
    private VBox createMessageBubble(String message) {
        VBox messageBubble = new VBox();
        messageBubble.setMaxWidth(MESSAGE_WIDTH);
        String[] messageLines = message.split("\\R", -1);
        for (String messageLine : messageLines) {
            messageBubble.getChildren().add(createMessageLine(messageLine));
        }
        return messageBubble;
    }

    private TextFlow createMessageLine(String messageLine) {
        TextFlow messageLineFlow = new TextFlow();
        messageLineFlow.setPrefWidth(MESSAGE_WIDTH - 20);
        messageLineFlow.setMinHeight(Region.USE_PREF_SIZE);

        Matcher matcher = TAG_PATTERN.matcher(messageLine);
        int textStart = 0;
        while (matcher.find()) {
            addTextSegment(messageLineFlow, messageLine.substring(textStart, matcher.start()));
            addTagChip(messageLineFlow, matcher.group(1));
            textStart = matcher.end();
        }
        addTextSegment(messageLineFlow, messageLine.substring(textStart));
        return messageLineFlow;
    }

    private void addTextSegment(TextFlow messageLine, String textSegment) {
        if (textSegment.isEmpty()) {
            if (messageLine.getChildren().isEmpty()) {
                Text blankText = new Text(" ");
                blankText.setFont(Font.font("Verdana", 13));
                messageLine.getChildren().add(blankText);
            }
            return;
        }
        Text text = new Text(textSegment);
        text.setFont(Font.font("Verdana", 13));
        messageLine.getChildren().add(text);
    }

    private void addTagChip(TextFlow messageLine, String tag) {
        Label tagChip = new Label("#" + tag.toLowerCase());
        tagChip.setStyle(getTagChipStyle(tag));
        tagChip.setMinHeight(Region.USE_PREF_SIZE);
        messageLine.getChildren().add(tagChip);
    }

    static String getTagChipStyle(String tag) {
        int colorIndex = Math.floorMod(tag.toLowerCase().hashCode(), TAG_COLORS.length);
        return "-fx-background-color: " + TAG_COLORS[colorIndex] + ";"
                + " -fx-background-radius: 6;"
                + " -fx-padding: 2 5 2 5;"
                + " -fx-font-family: 'Verdana';"
                + " -fx-font-size: 12;";
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
        assert dialogBox.getAlignment() == Pos.CENTER_RIGHT : "User dialog should be aligned to the right.";
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
        assert dialogBox.getAlignment() == Pos.CENTER_LEFT : "Larper dialog should be aligned to the left.";
        return dialogBox;
    }

    /**
     * Reorders the display picture and text so the avatar appears on the left.
     */
    private void flip() {
        assert getChildren().size() == 2 : "Dialog box should contain exactly a text node and an image node.";
        setAlignment(Pos.CENTER_LEFT);
        ObservableList<Node> nodes = FXCollections.observableArrayList(getChildren());
        FXCollections.reverse(nodes);
        getChildren().setAll(nodes);
    }
}
