package kuro.gui;

import static kuro.constants.StorageConstants.FILE_PATH;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import kuro.chatbot.Kuro;

/**
 * A GUI for kuro using FXML.
 */
public class Main extends Application {

    private Kuro kuro = new Kuro(FILE_PATH);

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle("Kuro");
            fxmlLoader.<MainWindow>getController().startKuro(kuro); // inject the Kuro instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

