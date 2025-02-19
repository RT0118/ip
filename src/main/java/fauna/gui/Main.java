package fauna.gui;

import java.io.IOException;

import fauna.ui.Fauna;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Main GUI Application for Fauna
 */
public class Main extends Application {
    private final Fauna fauna = new Fauna("./fauna.txt");

    /**
     * Creates the GUI scene and shows the stage
     * @param stage
     */
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.getIcons().add(new Image(
                    this.getClass().getResourceAsStream("/images/user_sapling.png")));
            stage.setScene(scene);
            stage.setTitle("Fauna");
            fxmlLoader.<MainWindow>getController().setFauna(fauna);
            fxmlLoader.<MainWindow>getController().showWelcome();
            stage.show();

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * save progress before exiting
     */
    @Override
    public void stop() {
        this.fauna.saveAndCleanup();
    }
}
