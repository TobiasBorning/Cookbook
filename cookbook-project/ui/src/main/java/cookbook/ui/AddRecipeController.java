
package cookbook.ui;

import cookbook.accessdata.CookbookAccess;
import cookbook.core.Recipe;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Controller class for adding a recipe in the UI.
 */
public class AddRecipeController {

  private Recipe recipe = new Recipe();
  private CookbookAccess cookbookAccess;

  @FXML
  private VBox ingredientsContainer;
  @FXML
  private TextField recipeName;
  @FXML
  private TextArea recipeDescription;
  @FXML
  private TextField origin;
  @FXML
  private TextField type;
  @FXML
  private CheckBox veganCheckBox;
  @FXML
  private CheckBox lactosefreeCheckBox;
  @FXML
  private CheckBox glutenFreeCheckBox;

  @FXML
  private Label feedbackLabel;

  private int ingredientCount = 0;

  /**
   * Adds a new ingredient input field into the UI.
   *
   * @param e The action event triggering the addition.
   */
  public void addIngredient(final ActionEvent e) {
    ingredientCount++;

    Pane pane = new Pane();
    pane.setMinWidth(330);
    pane.setMaxWidth(400);
    pane.setMinHeight(40);
    pane.setStyle("-fx-padding: 10; -fx-border-width: 0 0 3 0; -fx-border-color: #000;");

    TextField amount = new TextField();
    amount.setId("ingredientAmount" + ingredientCount);
    amount.setPromptText("amount");
    amount.setMaxWidth(100);
    amount.setLayoutX(130);
    amount.setLayoutY(10);

    TextField ingredientName = new TextField();
    ingredientName.setId("ingredientName" + ingredientCount);
    ingredientName.setPromptText("ingredientname");
    ingredientName.setMaxWidth(100);
    ingredientName.setLayoutX(10);
    ingredientName.setLayoutY(10);

    pane.getChildren().addAll(ingredientName, amount);
    ingredientsContainer.getChildren().add(pane);
  }

  /**
   * Adds the current recipe to the cookbook.
   *
   * @param e The action event triggering the save operation.
   * @throws IOException If an input or output exception occurred.
   */
  public void addToCookbook(final ActionEvent e) throws IOException {
    Map<String, String> ingredients = new HashMap<>();
    String recipeNameString = recipeName.getText();
    String descriptionString = recipeDescription.getText();
    String inputOrigin = origin.getText();
    String inputType = type.getText().equals("") ? "Unknown" : type.getText();
    boolean isVegetarian = veganCheckBox.isSelected();
    boolean isLactoseFree = lactosefreeCheckBox.isSelected();
    boolean isGlutenFree = glutenFreeCheckBox.isSelected();

    for (Node node : ingredientsContainer.getChildren()) {
      if (node instanceof Pane) {
        Pane pane = (Pane) node;
        TextField ingredientName = null;
        TextField amount = null;
        for (Node childNode : pane.getChildren()) {
          if (childNode instanceof TextField) {
            TextField textField = (TextField) childNode;
            if ("ingredientname".equals(textField.getPromptText())) {
              ingredientName = textField;
            } else if ("amount".equals(textField.getPromptText())) {
              amount = textField;
            }
            if (ingredientName != null && amount != null && !ingredientName.getText().equals("")) {
              ingredients.put(ingredientName.getText(), amount.getText());
            }
          }
        }
      }
    }
    this.recipe = new Recipe(recipeNameString, ingredients, inputOrigin, inputType,
        descriptionString, false, isVegetarian, isGlutenFree, isLactoseFree);
    if (addRecipe(recipe)) {
      switchToMainScene(e);
    } else {
      feedbackLabel.setText("Name already exists!");
    }
  }

  /**
   * Adds the given recipe to the cookbook.
   *
   * @param recipe The recipe to add.
   */
  private boolean addRecipe(Recipe recipe) {
    try {
      cookbookAccess.addRecipe(recipe);
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    } catch (RuntimeException e) {
      System.out.println("Error adding recipe: " + e.getMessage());
      return false;
    } 
  }

  /**
   * Switches to the main scene.
   *
   * @param e The action event triggering the switch.
   * @throws IOException If an input or output exception occurred.
   */
  public void switchToMainScene(ActionEvent e) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("CookbookApp.fxml"));
    Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Sets the cookbook access.
   *
   * @param cookbookAccess The cookbook access.
   */
  public void setCookbookAccess(CookbookAccess cookbookAccess) {
    this.cookbookAccess = cookbookAccess;
  }
}
