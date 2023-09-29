package cookbook.ui;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.Map.Entry;

import cookbook.core.Cookbook;
import cookbook.core.Recipe;
import cookbook.json.CookbookHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
//import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;



public class AppController {

  private Cookbook cookbook = new Cookbook();

  @FXML
  private VBox recipeList;
  @FXML
  private TextField searchField;
  @FXML
  private Button searchButton;
  @FXML
  private Label feedbackLabel;
  @FXML
  private ChoiceBox filterOrigin;
  @FXML
  private Button applyFilterButton;
  
  public void initialize() {

    CookbookHandler ch = new CookbookHandler();
    try {
      cookbook = ch.readFromFile("../cookbook.json");
    } catch (FileNotFoundException e) { 
      feedbackLabel.setText("File not found");
    }
    
    recipeList.setMinHeight(cookbook.getRecipes().size()*130);
    fillCookbook(cookbook.getRecipes());

    
  }

  private void fillCookbook(Collection<Recipe> cookbooklist) {
    recipeList.getChildren().clear();
    for(Recipe recipe : cookbooklist) {
      //lager pane til å vise oppskrift
      Pane pane = new Pane();
          pane.setMinWidth(330);
          pane.setMaxWidth(400);
          pane.setMinHeight(70);
          pane.setStyle("-fx-padding: 10 10 10 10; -fx-border-width: 0px 0px 3px 0px; -fx-border-color: #000000;");

      //overskrift med navn på recipe
      Label recipeName = new Label(recipe.getName());
        Font font = Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 16);
        recipeName.setFont(font);
        recipeName.setLayoutX(10);
        recipeName.setLayoutY(10);

      // Add remove button
      Button buttonRemove = new Button("Remove");
      buttonRemove.setLayoutX(pane.getMinWidth() - buttonRemove.getMinWidth()); // Adjust the x-coordinate as needed
      buttonRemove.setLayoutY(10); // Adjust the y-coordinate as needed
      buttonRemove.onActionProperty().set(e -> {
        removeRecipe();
      });
      
      // Add view button
      Button buttonView = new Button("View");
      buttonView.setLayoutX(pane.getMinWidth() - buttonView.getMinWidth() - buttonRemove.getMinWidth() - 50); // Adjust the x-coordinate as needed
      buttonView.setLayoutY(10); // Adjust the y-coordinate as needed
      buttonView.onActionProperty().set(e -> {
        //viewRecipe(recipe);
        System.out.println("View recipe");
      });
      
      
      
      pane.getChildren().addAll(recipeName, buttonView, buttonRemove);
      recipeList.getChildren().add(pane);
    }

    //Fill filter dropdown menu with origins from cookbook
    fillFilterDropdown();
  }

  public void kodesomkanbrukes() {
    Recipe recipe  = new Recipe();

    Pane pane = new Pane();
          pane.setMinWidth(330);
          pane.setMaxWidth(330);
          pane.setMinHeight(70);
          pane.setStyle("-fx-padding: 10 10 10 10;");

      //overskrift med navn på recipe
      Label recipeName = new Label(recipe.getName());
        Font font = Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 16);
        recipeName.setFont(font);
        recipeName.setLayoutX(10);

      //legger til liste med ingredienser
      Label ingredients = new Label("");
        ingredients.setLayoutX(10);
        for(Entry<String,Double> ingredient : recipe.getIngredients().entrySet()) {
          String text = ingredients.getText();
          ingredients.setText(text + "\n" + ingredient.getKey().toString() + ":  " + ingredient.getValue());
        }
        ingredients.setText(ingredients.getText() + "\n");
      
  }

  public void search() {
    String search = searchField.getText();
    if (search.isEmpty()) {
      fillCookbook(cookbook.getRecipes());
      feedbackLabel.setText("");
    }
    else {
      Collection<Recipe> searched = cookbook.filterRecipies(recipe -> recipe.getName().toLowerCase().contains(search.toLowerCase()));
      if (searched.isEmpty()) {
        feedbackLabel.setText("No recipes matching search");
      }
      else {
        fillCookbook(searched);
        feedbackLabel.setText("");
      }
    }
  }


  private void fillFilterDropdown() {
    //create empty set to add origins to
    Set<String> origins = new HashSet<>();
    //get recipes
    Collection<Recipe> recipes = cookbook.getRecipes();

    //Add no filter option
    origins.add("All origins");

    //Add all origin countries from the recipes in cookbook
    for (Recipe recipe : recipes) {
      origins.add(recipe.getOriginCountry());
    }
    //Add origins to the dropdown menu, if not duplicate
    for (String origin : origins) {
      if (!filterOrigin.getItems().contains(origin)) {
        filterOrigin.getItems().add(origin);
      }
    }
    //Set default value of dropdown
    filterOrigin.setValue("All origins");

  }

  public void filterByOrigin() {
    //get value from dropdown
    String filterValue = (String)filterOrigin.getValue();
    //fill cookbook with filtered recipes
    if (filterValue.equals("All origins")) {
      fillCookbook(cookbook.getRecipes());
    }
    else{
      Collection<Recipe> searched = cookbook.filterRecipies(recipe -> recipe.getOriginCountry().equals(filterValue));
      fillCookbook(searched);
    }
    filterOrigin.setValue(filterValue);
  }

  public void removeRecipe() {
    System.out.println("Recipe removed");
  }
}




