package cookbook.ui;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The main entry point for the Cookbook application.
 * This class extends Application and sets up the primary stage
 * for the JavaFX user interface.
 */
public class CookbookApp extends Application {

  /**
   * Starts the application by loading the primary stage with the
   * specified FXML layout.
   *
   * @param stage The primary stage for this application.
   * @throws IOException If the FXML file cannot be loaded.
   */
  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("CookbookApp.fxml"));
    Parent parent = fxmlLoader.load();
    Scene primaryStage = new Scene(parent);
    primaryStage.getProperties().put("Main", "1");
    stage.setScene(primaryStage);
    stage.show();
  }

  /**
   * The main method that launches the JavaFX application.
   *
   * @param args Command line arguments.
   */
  public static void main(String[] args) {
    launch();
  }
}