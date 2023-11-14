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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Controller class for editing recipes.
 * This class provides the user interface logic to update existing recipes with new information.
 */
public class EditRecipeController {

  private Stage stage;
  private Scene scene;
  private Recipe oldRecipe = new Recipe();
  private Recipe newRecipe = new Recipe();
  private CookbookAccess cookbookAccess;

  @FXML
  private VBox ingredientsContainer;

  @FXML
  private TextArea recipeDescription;

  @FXML
  private TextField originTextField;

  @FXML 
  private TextField typeTextField;

  @FXML
  private Button back;

  @FXML
  private Button addIngredientButton;

  @FXML
  private Button saveChangesButton;

  @FXML 
  private Label title;

  @FXML
  private CheckBox veganCheckBox;

  @FXML
  private CheckBox lactoseFreeCheckBox;

  @FXML
  private CheckBox glutenFreeCheckBox;

  @FXML
  private Label feedbackLabel;

  private int ingredientCount = 0;

  /**
   * Loads a recipe into the editor.
   * Initializes the UI with the values from the given recipe, allowing them to be
   * edited. This prepares all the input fields with the recipe's current details.
   *
   * @param recipe The recipe to load for modification 
   *
   */
  public void loadRecipe(Recipe recipe) {
    this.oldRecipe = recipe;
    this.newRecipe.setName(oldRecipe.getName());
    recipeDescription.setText(recipe.getDescription());
    originTextField.setText(recipe.getOriginCountry());
    typeTextField.setText(recipe.getType());
    title.setText("Edit " + recipe.getName());
    newRecipe.setFavorite(oldRecipe.getFavorite());
    veganCheckBox.setSelected(oldRecipe.getVegan());
    lactoseFreeCheckBox.setSelected(oldRecipe.getLactoseFree());
    glutenFreeCheckBox.setSelected(oldRecipe.getGlutenFree());
    
    for (Map.Entry<String, String> ingredient : recipe.getIngredients()
        .entrySet()) {
      addIngredient();
      TextField ingredientName = (TextField) 
          ingredientsContainer.lookup("#ingredientName" + ingredientCount);
      TextField amount = (TextField) ingredientsContainer
          .lookup("#ingredientAmount" + ingredientCount);
      ingredientName.setText(ingredient.getKey());
      amount.setText(ingredient.getValue());
    }
  }

  /**
   * Adds a new ingredient input field to the editor.
   * This method dynamically creates a new set of text 
   * fields for inputting an ingredient's name and amount.
   */
  public void addIngredient() {
    //Tracks which textfields should be targeted.
    ingredientCount++;

    Pane pane = new Pane();
    pane.setMinWidth(330);
    pane.setMaxWidth(400);
    pane.setMinHeight(40);
    pane.setStyle("-fx-padding: 10 10 10 10; -fx-border-width: "  
        + "0px 0px 3px 0px; -fx-border-color: #000000;");

    TextField amount = new TextField();
    amount.setId("ingredientAmount" + ingredientCount);
    amount.setPromptText("amount");
    amount.setMaxWidth(100);
    amount.setLayoutX(130); // Adjust the x-coordinate as needed
    amount.setLayoutY(10); // Adjust the y-coordinate as needed

    TextField ingredientName = new TextField();
    ingredientName.setId("ingredientName" +  ingredientCount);
    ingredientName.setPromptText("ingredientname");
    ingredientName.setMaxWidth(100);
    ingredientName.setLayoutX(10); // Adjust the x-coordinate as needed
    ingredientName.setLayoutY(10); // Adjust the y-coordinate as needed

    pane.getChildren().addAll(ingredientName, amount);
    ingredientsContainer.getChildren().add(pane);
  }

  /**
   * Saves the changes made to the recipe.
   * This method constructs a new recipe instance 
   * from the edited values and updates the recipe in the cookbook access.
   *
   * @param event The event that triggered the save action.
   * @throws IOException If an I/O error occurs during saving or scene switching.
   */
  public void saveChanges(ActionEvent event) throws IOException {

    Map<String, String> ingredients = new HashMap<>();
    String descriptionString = null;
    TextField ingredientName = null;
    TextField amount = null;
    String inputOrigin = null;
    String inputType = "Unknown";
    Boolean isVegan = newRecipe.getVegan();
    Boolean isLactoseFree = newRecipe.getLactoseFree();
    Boolean isGlutenFree = newRecipe.getGlutenFree();
    
    isVegan = veganCheckBox.isSelected();
    isLactoseFree = lactoseFreeCheckBox.isSelected();
    isGlutenFree = glutenFreeCheckBox.isSelected();
    
    if (recipeDescription.getText() != null) {
      descriptionString = recipeDescription.getText();
    }
    if (originTextField.getText().equals("")) {
      feedbackLabel.setText("Origin is required!");
      return;
    } else {
      inputOrigin = originTextField.getText();
    }
    if (typeTextField.getText() != null) {
      newRecipe.setType(typeTextField.getText());
      inputType = newRecipe.getType();
    } 

    for (Node node : ingredientsContainer.getChildren()) {
      if (node instanceof Pane) {
        Pane pane = (Pane) node;
        for (Node childNode : pane.getChildren()) {
          if (childNode instanceof TextField) {
            TextField textField = (TextField) childNode;
            if (textField.getPromptText().equals("ingredientname")) {
              ingredientName = textField;
            } else if (textField.getPromptText().equals("amount")) {
              amount = textField;
            }
            if (ingredientName != null && amount != null  && !ingredientName.getText().equals("")) {
              String ingredientNameString = ingredientName.getText();
              String amountString = amount.getText();
              ingredients.put(ingredientNameString, amountString);
            }
          }
        }
      }
    }
    this.newRecipe = new Recipe(oldRecipe.getName(), ingredients, inputOrigin, inputType, 
        descriptionString, newRecipe.getFavorite(), isVegan, isGlutenFree, isLactoseFree);

    try {
      cookbookAccess.updateRecipe(newRecipe); 
    } catch (RuntimeException e) {
      System.out.println("Could not update cookbook \n" + e.getMessage());
    }
    switchToMainScene(event);
  }

  /**
   * Switches from the EditRecipe scene to the main scene.
   * This method handles the transition back to the 
   * main cookbook view after editing is completed or cancelled.
   *
   * @param e The event that triggered the switch action.
   * @throws IOException If an I/O error occurs during the scene switching.
   */
  public void switchToMainScene(ActionEvent e) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("CookbookApp.fxml"));
    Parent root = loader.load();
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

  /**
   * Switches the view to display a single recipe.
   * This method is responsible for changing the UI scene to a detailed view of a single recipe.
   *
   * @param event The event that triggered the switch to the view recipe scene.
   * @throws IOException If an I/O error occurs during the scene switching.
   */
  public void switchToViewRecipe(ActionEvent event) throws IOException {
    // load recipeview
    FXMLLoader loader = new FXMLLoader(getClass().getResource("RecipeView.fxml")); 
    Parent root = loader.load(); 
    // update scene
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); 
    scene = new Scene(root); 
    stage.setScene(scene); 
    stage.show(); 
    // send recipe to RecipeViewController
    RecipeViewController viewController = loader.getController(); 
    viewController.loadRecipe(oldRecipe);
    viewController.setCookbookAccess(cookbookAccess);
  }  
}
