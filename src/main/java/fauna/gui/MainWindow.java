package fauna.gui;

import fauna.ui.Fauna;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Fauna fauna;

    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/user_sapling.png"));
    private final Image faunaImage = new Image(this.getClass().getResourceAsStream("/images/fauna.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects the Fauna instance
     */
    public void setFauna(Fauna fauna) {
        this.fauna = fauna;
    }

    /**
     * Show the welcome message as a Fauna response on start
     */
    public void showWelcome() {
        String firstMessage = this.fauna.getWelcomeMessageForGui();
        dialogContainer.getChildren().add(DialogBox.getFaunaDialogBox(firstMessage, faunaImage));
    }

    private void showChatDialogBoxes(String userInput, String botResponse) {
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialogBox(userInput, userImage),
                DialogBox.getFaunaDialogBox(botResponse, faunaImage)
        );
    }

    private void showExitMessage() {
        dialogContainer.getChildren().add(
                DialogBox.getFaunaDialogBox(
                        "THIS CHATBOT WILL SELF DESTRUCT IN 3 SECONDS",
                        new Image(this.getClass().getResourceAsStream("/images/bye.png"))));
    }

    private void performExitSequence() {
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(event -> Platform.exit());
        showExitMessage();
        delay.play();
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Fauna's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = this.fauna.getResponse(input);
        showChatDialogBoxes(input, response);

        if (input.startsWith("bye")) {
            performExitSequence();
        }

        userInput.clear();
    }
}
