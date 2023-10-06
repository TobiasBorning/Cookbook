package cookbook.ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
// import java.util.List;
// import java.util.Map.Entry;

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
import javafx.scene.control.ChoiceBox;
//import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;



public class AppController {

  private Stage stage;
  private Scene scene;

  private Recipe sendRecipe;
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
  private ChoiceBox<String> filterOrigin;
  @FXML
  private Button applyFilterButton;
  @FXML
  private Button allRecipesButton;
  


  public void initialize() {
    System.out.println("Initialize!");

    // read cookbook from file
    CookbookHandler ch = new CookbookHandler();
    try {
      cookbook = ch.readFromFile("../persistence/cookbook.json");
    } catch (FileNotFoundException e) { 
      feedbackLabel.setText("File not found");
    }
    // set Vbox height to fit all recipes
    recipeList.setMinHeight(cookbook.getRecipes().size()*130);
    // fill cookbook with all recipes
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
        removeRecipe(recipe);
      });
      
      // Add view button
      Button buttonView = new Button("View");
      buttonView.setLayoutX(pane.getMinWidth() - buttonView.getMinWidth() - buttonRemove.getMinWidth() - 50); // Adjust the x-coordinate as needed
      buttonView.setLayoutY(10); // Adjust the y-coordinate as needed
      buttonView.onActionProperty().set(e -> {
        //viewRecipe(recipe);
        try {
          sendRecipe = recipe;
          switchToViewRecipe(e);
        }
        catch (IOException ex) {
          System.err.println(ex);
        }
      });
      
      pane.getChildren().addAll(recipeName, buttonView, buttonRemove);
      recipeList.getChildren().add(pane);
    }

    //Fill filter dropdown menu with origins from cookbook
    fillFilterDropdown();
  }

  public void search() {
    // get search string
    String search = searchField.getText();
    if (search.isEmpty()) {
      // if search is empty, fill cookbook with all recipes
      fillCookbook(cookbook.getRecipes());
      feedbackLabel.setText("");
    }
    else {
      // if search is not empty, fill cookbook with matching recipes
      Collection<Recipe> searched = cookbook.filterRecipies(recipe -> recipe.getName().toLowerCase().contains(search.toLowerCase()));
      if (searched.isEmpty()) {
        // if no recipes match search, give feedback
        feedbackLabel.setText("No recipes matching search");
      }
      else {
        // if recipes match search, make feedback empty and fill cookbook
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

  //Switches from main scene to AddRecipe scene
  public void switchToAddRecipe(ActionEvent event) throws IOException {
    // load AddRecipe
    FXMLLoader loader = new FXMLLoader(getClass().getResource("AddRecipe.fxml"));
    Parent root = loader.load();
    // update scene
    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }


  //Switches from main scene to ViewRecipe scene
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
    viewController.loadRecipe(sendRecipe);
  }

  

  // remove recipe from cookbook
  public void removeRecipe(Recipe recipe) {
    // initialize new cookbookhandler
    CookbookHandler ch = new CookbookHandler();
    // remove recipe from cookbook class
    cookbook.removeRecipe(recipe);
    // remove recipe from the cookbook.json file
    try {
      ch.writeToFile(cookbook, "../cookbook.json");
      setFeedbackLabel("Removed recipe");
    } catch (FileNotFoundException e) {
      setFeedbackLabel("File not found");
    }
    // update the cookbook view
    fillCookbook(cookbook.getRecipes());
  }

  public void setFeedbackLabel(String feedback){
    feedbackLabel.setText(feedback);
  }
}


