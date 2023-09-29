package cookbook.ui;

import java.io.IOException;

import cookbook.core.Cookbook;
import cookbook.core.Recipe;
import cookbook.json.CookbookHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class RecipeViewController {

  private Cookbook cookbook = new Cookbook();
  
  @FXML
  private Label ingredients;
  @FXML 
  private Label RecipeName;
  @FXML
  private Button allRecipesButton;
  @FXML
  private Label description;

  public void switchToMainScene() throws IOException {
    // Laster inn main scene
    FXMLLoader loader = new FXMLLoader(getClass().getResource("ui.src.main.java.resources.cookbook.ui.CookbookApp.fxml"));
    Parent mainScene = loader.load();

    // Hent kontrolleren for den andre scenen
    AppController mainSceneController = loader.getController();

    // Få tak i rot-node for den nåværende scenen
    Parent currentSceneRoot = allRecipesButton.getScene().getRoot();

    // Bytt fra nåværende scene til den andre scenen
    Scene scene = new Scene(mainScene);
    Stage stage = (Stage) currentSceneRoot.getScene().getWindow();
    stage.setScene(scene);

    mainSceneController.initialize();
  }
}
