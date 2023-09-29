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
import javafx.stage.Stage;

public class RecipeViewController {
  
  private Recipe recipe;
  @FXML
  private Label recipeName;
  @FXML
  private Label description;
  @FXML
  private Label ingredients;

  public void loadRecipe(Recipe recipe) {
    this.recipe = recipe;
    recipeName.setText(recipe.getName());
    description.setText(recipe.getDescription());
    //legger til liste med ingredienser
    for(Entry<String,Double> ingredient : recipe.getIngredients().entrySet()) {
      String text = ingredients.getText();
      ingredients.setText(text + "\n" + ingredient.getKey().toString() + ":  " + ingredient.getValue());
    }
    ingredients.setText(ingredients.getText() + "\n");
  }

  public void switchToMainScene(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("CookbookApp.fxml"));
    Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }
}
