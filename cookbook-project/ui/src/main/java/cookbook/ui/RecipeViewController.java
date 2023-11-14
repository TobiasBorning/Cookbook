package cookbook.ui;

import cookbook.accessdata.CookbookAccess;
import cookbook.core.Recipe;
import java.io.IOException;
import java.util.Map.Entry;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;


/**
 * Controller class for the recipe view in the cookbook application.
 * This class is responsible for displaying the details of a recipe.
 * It also provides the functionality to navigate back to the main scene
 * or to the edit recipe scene.
 */
public class RecipeViewController {

  private Stage stage;
  private Scene scene;
  private CookbookAccess cookbookAccess;

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
  @FXML
  private Label vegan;
  @FXML
  private Label lactosefree;
  @FXML
  private Label glutenfree;

  /**
   * Loads the recipe details into the view.
   *
   * @param loadedRecipe The recipe object containing the details to be displayed.
   */
  public void loadRecipe(Recipe loadedRecipe) {
    this.recipe = loadedRecipe;
    origin.setFont(new Font("Arial", 16));
    description.setFont(new Font("Arial", 14));
    type.setFont(new Font("Arial", 14));
    ingredients.setFont(new Font("Arial", 14));
    recipeName.setText(recipe.getName());
    description.setWrapText(true);
    description.setText(recipe.getDescription());
    type.setText(recipe.getType());
    origin.setText(recipe.getOriginCountry());
    if (recipe.getVegan()) {
      vegan.setText("Yes");
    } else if (!recipe.getVegan()) {
      vegan.setText("No");
    }
    if (recipe.getLactoseFree()) {
      lactosefree.setText("Yes");
    } else if (!recipe.getLactoseFree()) {
      lactosefree.setText("No");
    }
    if (recipe.getGlutenFree()) {
      glutenfree.setText("Yes");
    } else if (!recipe.getGlutenFree()) {
      glutenfree.setText("No");
    }
    for (Entry<String, String> ingredient : recipe.getIngredients().entrySet()) {
      String text = ingredients.getText();
      ingredients.setText(text + "\n" + ingredient.getKey().toString() 
          + ":  " + ingredient.getValue());
    }
    ingredients.setText(ingredients.getText() + "\n");
  }

  /**
   * Switches the view back to the main cookbook scene.
   *
   * @param event The action event that triggered the switch.
   * @throws IOException If the fxml file for the main scene cannot be loaded.
   */
  public void switchToMainScene(final ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("CookbookApp.fxml"));
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Switches the view to the edit recipe scene, allowing the user to modify the recipe details.
   *
   * @param event The action event that triggered the switch.
   * @throws IOException If the fxml file for the edit recipe scene cannot be loaded.
   */
  public void switchToEditRecipeScene(ActionEvent event) throws IOException {
    // load recipeview
    FXMLLoader loader = new FXMLLoader(getClass().getResource("EditRecipe.fxml"));
    Parent root = loader.load();
    // update scene
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
    // send recipe to RecipeViewController
    EditRecipeController editController = loader.getController();
    editController.loadRecipe(recipe);
    System.out.println("Send" + cookbookAccess + "to edit recipe controller");
    editController.setCookbookAccess(cookbookAccess);
  } 

  /**
   * Sets the cookbook access for this controller.
   *
   * @param cookbookAccess The cookbook access to be used by this controller.
   */
  public void setCookbookAccess(CookbookAccess cookbookAccess) {
    this.cookbookAccess = cookbookAccess;
  }

}
