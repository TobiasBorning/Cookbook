package cookbook.ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cookbook.accessdata.CookbookAccess;
import cookbook.accessdata.RemoteCookbookAccess;
import cookbook.core.Cookbook;
import cookbook.core.Recipe;
import cookbook.json.CookbookHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EditRecipeController {
    private Stage stage;
    private Scene scene;
    //private Cookbook cookbook = new Cookbook();
    private Recipe oldRecipe = new Recipe();
    private Recipe newRecipe = new Recipe();
    private CookbookAccess cookbookAccess = new RemoteCookbookAccess();

    @FXML
    private VBox ingredientsContainer;

    @FXML
    private TextArea recipeDescription;

    @FXML
    private TextField origin;

    @FXML 
    private TextField type;

    @FXML
    private Button back;

    @FXML
    private Button addIngredientButton;

    @FXML
    private Button saveChangesButton;

    @FXML 
    private Label title;

    private int ingredientCount = 0;


    //TODO: Initialize the recipe object with the recipe that was clicked on.
    public void loadRecipe(Recipe recipe){
        this.oldRecipe = recipe;
        this.newRecipe.setName(oldRecipe.getName());
        // System.out.println("Recipe name: " + recipe.getName());
        recipeDescription.setText(recipe.getDescription());
        origin.setText(recipe.getOriginCountry());
        type.setText(recipe.getType());
        title.setText("Edit " + recipe.getName());
        newRecipe.setFavorite(oldRecipe.isFavorite());
        for (Map.Entry<String, String> ingredient : recipe.getIngredients().entrySet()) {
            addIngredient();
            TextField ingredientName = (TextField) ingredientsContainer.lookup("#ingredientName" + ingredientCount);
            TextField amount = (TextField) ingredientsContainer.lookup("#ingredientAmount" + ingredientCount);
            ingredientName.setText(ingredient.getKey());
            amount.setText(ingredient.getValue());
        }
    }
    
    public void addIngredient() {
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

    public void saveChanges(ActionEvent e) throws IOException {

    Map<String, String> ingredients = new HashMap<>();
    String descriptionString = null;
    TextField ingredientName = null;
    TextField amount = null;
    String inputOrigin = null;
    String inputType = "Unknown";
    
    if (!recipeDescription.getText().equals(null)) {
      descriptionString = recipeDescription.getText();
    }
    if (!origin.getText().equals(null)) {
      inputOrigin = origin.getText();
    }
    if (!type.getText().equals(null)) {
      newRecipe.setType(type.getText());
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
    //må endre i konstruktøren senere !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! fjerne false verdiene 
    this.newRecipe = new Recipe(oldRecipe.getName(), ingredients, inputOrigin, inputType, descriptionString, newRecipe.isFavorite(), false, false, false);
    cookbookAccess.updateRecipe(newRecipe);

    //Calls on method that switches to main scene
    switchToMainScene(e);
    }

    //Switches from AddRecipe scene to main scene
    public void switchToMainScene(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("CookbookApp.fxml"));
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void setCookbookAccess(CookbookAccess cookbookAccess) {
        this.cookbookAccess = cookbookAccess;
    }

    public void switchToViewRecipe(ActionEvent event) throws IOException {
        // load recipeview
        FXMLLoader loader = new FXMLLoader(getClass().getResource("RecipeView.fxml"));
        Parent root = loader.load();
        // update scene
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        // send recipe to RecipeViewController
        RecipeViewController viewController = loader.getController();
        viewController.loadRecipe(newRecipe);
      }  
}
