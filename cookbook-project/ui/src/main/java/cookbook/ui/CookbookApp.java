package cookbook.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class CookbookApp extends Application {

  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("CookbookApp.fxml"));
    Parent parent = fxmlLoader.load();
    Scene primaryStage = new Scene(parent);
    primaryStage.getProperties().put("Main", "1");
    stage.setScene(primaryStage);
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}