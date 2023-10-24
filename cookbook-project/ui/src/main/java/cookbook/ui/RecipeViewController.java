package cookbook.ui;

import java.io.IOException;
import java.util.Map.Entry;

import cookbook.core.Recipe;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class RecipeViewController {
  
  @FXML
  private Recipe recipe;
  @FXML
  private Label recipeName;
  @FXML
  private Label description;
  @FXML
  private Label ingredients;
  @FXML
  private Label origin;
  @FXML
  private Label type;

  public void loadRecipe(Recipe recipe) {
    this.recipe = recipe;

    origin.setFont(new Font("Arial", 16));
    description.setFont(new Font("Arial", 14));
    type.setFont(new Font("Arial",14));
    ingredients.setFont(new Font("Arial", 14));

    recipeName.setText(recipe.getName());
    description.setWrapText(true);
    description.setText(recipe.getDescription());
    type.setText(recipe.getType());
    origin.setText(recipe.getOriginCountry());
    //legger til liste med ingredienser
    for (Entry<String, String> ingredient : recipe.getIngredients().entrySet()) {
      String text = ingredients.getText();
      ingredients.setText(text + "\n" + ingredient.getKey().toString() + ":  " + ingredient.getValue());
    }
    ingredients.setText(ingredients.getText() + "\n");
  }

  public void switchToMainScene(final ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("CookbookApp.fxml"));
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }
}
