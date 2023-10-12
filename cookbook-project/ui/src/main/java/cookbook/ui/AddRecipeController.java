package cookbook.ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import cookbook.core.Cookbook;
import cookbook.core.Recipe;
import cookbook.json.CookbookHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddRecipeController {

  private Cookbook cookbook = new Cookbook();
  private Recipe recipe = new Recipe();
  private AppController ac;

  @FXML
  private VBox ingredientsContainer;

  @FXML
  private TextField recipeName;

  @FXML
  private TextArea recipeDescription;

  @FXML
  private TextField origin;

  private int ingredientCount = 0;

  public void addIngredient(final ActionEvent e) {
    //Tracks which textfields should be targeted.
    ingredientCount++;

    Pane pane = new Pane();
    pane.setMinWidth(330);
    pane.setMaxWidth(400);
    pane.setMinHeight(40);
    pane.setStyle("-fx-padding: 10 10 10 10; -fx-border-width: 0px 0px 3px 0px; -fx-border-color: #000000;");

    TextField amount = new TextField();
    amount.setId("ingredientAmount" + ingredientCount);
    System.out.println(amount.getId());
    amount.setPromptText("amount");
    amount.setMaxWidth(100);
    amount.setLayoutX(130); // Adjust the x-coordinate as needed
    amount.setLayoutY(10); // Adjust the y-coordinate as needed

    TextField ingredientName = new TextField();
    ingredientName.setId("ingredientName" +  ingredientCount);
    System.out.println(ingredientName.getId());
    ingredientName.setPromptText("ingredientname");
    ingredientName.setMaxWidth(100);
    ingredientName.setLayoutX(10); // Adjust the x-coordinate as needed
    ingredientName.setLayoutY(10); // Adjust the y-coordinate as needed

    pane.getChildren().addAll(ingredientName, amount);
    ingredientsContainer.getChildren().add(pane);
  }

  public void addToCookbook(final ActionEvent e) throws IOException {

    Map<String, String> ingredients = new HashMap<>();
    String recipeNameString = null;
    String descriptionString = null;
    TextField ingredientName = null;
    TextField amount = null;
    String inputOrigin = null;
    
    if (!recipeName.getText().equals(null)) {
      recipeNameString = recipeName.getText();
    }
    if (!recipeDescription.getText().equals(null)) {
      descriptionString = recipeDescription.getText();
    }
    if (!origin.getText().equals(null)) {
      inputOrigin = origin.getText();
    }
    
    for (Node node : ingredientsContainer.getChildren()) {
      if (node instanceof Pane) {
        Pane pane = (Pane) node;

        for (Node childNode : pane.getChildren()) {
          if (childNode instanceof TextField) {
            TextField textField = (TextField) childNode;
            if (textField.getPromptText().equals("ingredientname")) {
              ingredientName = textField;
            }
            else if (textField.getPromptText().equals("amount")) {
              amount = textField;
            }
            if (ingredientName != null && amount != null) {
              String ingredientNameString = ingredientName.getText();
              String amountString = amount.getText();
              ingredients.put(ingredientNameString, amountString);
            }
          }
        }
      }
    }
    this.recipe = new Recipe(recipeNameString, ingredients, inputOrigin, descriptionString);
    addRecipe(recipe);

    //Calls on method that switches to min scene
    switchToMainScene(e);

  }

  // add recipe to cookbook
  public void addRecipe(Recipe recipe) {
    // initialize new cookbookhandler
    CookbookHandler ch = new CookbookHandler();

    try {
      cookbook = ch.readFromFile("../persistence/cookbook.json");
    } catch (FileNotFoundException e) {
    }
    // adds recipe to cookbook class
    cookbook.addRecipe(recipe);

    // adds recipe to the cookbook.json file
    try {
      ch.writeToFile(cookbook, "../persistence/cookbook.json");
    } catch (FileNotFoundException e) {
    }
  }

  //Switches from AddRecipe scene to main scene
  public void switchToMainScene(ActionEvent e) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("CookbookApp.fxml"));
    Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }
}
