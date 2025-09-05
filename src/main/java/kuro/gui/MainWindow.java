package kuro.gui;

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
import kuro.chatbot.Kuro;
import kuro.constants.Messages;

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

    private Kuro kuro;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/User.png"));
    private Image kuroImage = new Image(this.getClass().getResourceAsStream("/images/Kuro.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects the Kuro instance and show welcome dialogBox.
     *
     * @param d kuro object.
     **/
    public void startKuro(Kuro d) {
        kuro = d;
        dialogContainer.getChildren().add(DialogBox.getKuroDialog(Messages.WELCOME_MESSAGE, kuroImage));
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Kuro's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = kuro.executeCommand(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getKuroDialog(response, kuroImage)
        );
        userInput.clear();
        if (response.equals(Messages.GOODBYE_MESSAGE)) {
            // Pause for 1 second before closing
            PauseTransition delay = new PauseTransition(Duration.seconds(1));
            delay.setOnFinished(event -> Platform.exit());
            delay.play();
        }
    }
}
