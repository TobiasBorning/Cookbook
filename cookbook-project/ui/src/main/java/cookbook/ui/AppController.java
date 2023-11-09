package cookbook.ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
// import java.util.List;
// import java.util.Map.Entry;

import cookbook.accessdata.CookbookAccess;
import cookbook.accessdata.LocalCookbookAccess;
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
import javafx.scene.control.CheckBox;
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
  private CookbookAccess cookbookAccess;
  private boolean remote;


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
  @FXML
  private ChoiceBox<String> typeFilter;
  @FXML
  private Button applyTypeFilterButton;
  @FXML
  private CheckBox favoritesCheckBox;
  @FXML
  private CheckBox veganCheckBox;
  @FXML
  private CheckBox lactosefreeCheckBox;
  @FXML
  private CheckBox glutenFreeCheckBox;

  public void initialize() {
    
    // set remote or local cookbook access depending on connection, override = true always connects to local
    setAccessType(false);

    cookbook = cookbookAccess.fetchCookbook();
    // set Vbox height to fit all recipes
    recipeList.setMinHeight(cookbook.getRecipes().size() * 130);
    // fill cookbook with all recipes
    fillCookbook(cookbook);
  }

  //TODO: Bruker vi egt. denne?
  public void fillDefaultCookbook() {
    Cookbook cookbook = new Cookbook();
    CookbookHandler ch = new CookbookHandler();
    try {
      cookbook = ch.readFromFile("../persistence/default-cookbook.json");
    } catch (FileNotFoundException e) { 
      feedbackLabel.setText("File not found");
    }
    fillCookbook(cookbook);
  }

  private void fillCookbook(Cookbook cookbook) {
    recipeList.getChildren().clear();
    Collection<Recipe> cookbooklist = cookbook.getRecipes();
    for (Recipe recipe : cookbooklist) {
      //lager pane til å vise oppskrift
      Pane pane = new Pane();
          pane.setMinWidth(330);
          pane.setMaxWidth(400);
          pane.setMinHeight(70);
          pane.setStyle("-fx-padding: 10 10 10 10; -fx-border-width: 0px 0px 3px 0px; -fx-border-color: #000000;");

      //overskrift med navn på recipe
      Label recipeName = new Label(recipe.getName());
      //satt label CSS id for testing
      recipeName.setId(recipe.getName() + "Recipe"); //ex: #TacoRecipe
        Font font = Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 16);
        recipeName.setFont(font);
        recipeName.setMaxWidth(200);
        recipeName.setLayoutX(10);
        recipeName.setLayoutY(10);

      // Add remove button
      Button buttonRemove = new Button("Remove");
      //satt button CSS id for testing
      buttonRemove.setId("remove"+recipe.getName()); //ex: #removeTaco
        buttonRemove.setLayoutX(pane.getMinWidth() - buttonRemove.getMinWidth()); // Adjust the x-coordinate as needed
        buttonRemove.setLayoutY(10); // Adjust the y-coordinate as needed
        buttonRemove.onActionProperty().set(e -> {
        removeRecipe(recipe);
      });
    
      // Add view button
      Button buttonView = new Button("View");
      //satt button CSS id for testing
      buttonView.setId("view"+recipe.getName()); //ex: #viewTaco
      
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

      //Add favorite button
      Button buttonFavorite = new Button("★");
      if (recipe.isFavorite()){
        buttonFavorite.setStyle("-fx-background-color: yellow;");
      }
      else {
        buttonFavorite.setStyle("-fx-background-color; ");
      }
      buttonFavorite.setId("favorite"+recipe.getName()); //ex: #removeTaco
        buttonFavorite.setLayoutX(pane.getMinWidth() - buttonView.getMinWidth() - buttonRemove.getMinWidth() - 87.5 - buttonFavorite.getMinWidth()); // Adjust the x-coordinate as needed
        buttonFavorite.setLayoutY(10); // Adjust the y-coordinate as needed
        buttonFavorite.onActionProperty().set(e -> {
          cookbookAccess.toggleFavorite(recipe);
          fillCookbook(cookbookAccess.fetchCookbook());
          favoritesCheckBox.setSelected(false);
        });
      pane.getChildren().addAll(recipeName, buttonView, buttonRemove, buttonFavorite);
      recipeList.getChildren().add(pane);
    }

    //Fill filter dropdown menu with origins from cookbook
    fillFilterDropdown();
    //Fill filter dropdown menu with types from cookbook
    fillTypeFilterDropdown();
  }

  public void search() {
    // get search string
    String search = searchField.getText();
    if (search.isEmpty()) {
      // if search is empty, fill cookbook with all recipes
      fillCookbook(cookbookAccess.fetchCookbook());
      feedbackLabel.setText("");
    } else {
      // if search is not empty, fill cookbook with matching recipes
      Cookbook searched = cookbookAccess.searchRecipe(search);
      if (searched == null || searched.getRecipes().isEmpty()) {
        // if no recipes match search, give feedback
        feedbackLabel.setText("No recipes matching search");
      } else {
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
      if (!filterOrigin.getItems().contains(origin) && origin != null) {
          filterOrigin.getItems().add(origin);
      }
    }
    //Set default value of dropdown
    filterOrigin.setValue("All origins");

  }

  public void filterByOrigin() {
    favoritesCheckBox.setSelected(false);
    resetPreferences();
    //get value from dropdown
    String filterValue = (String) filterOrigin.getValue();

    //fill cookbook with filtered recipes
    if (filterValue.equals("All origins")) {
      fillCookbook(cookbook);
    } else {
      fillCookbook(cookbookAccess.filterByOrigin(filterValue));
    }
    filterOrigin.setValue(filterValue);
  }

  private void fillTypeFilterDropdown() {
    //create empty set to add types to
    Set<String> types = new HashSet<>();
    //get recipes
    Collection<Recipe> recipes = cookbook.getRecipes();

    //Add no filter option
    types.add("All types");

    //Add all types from the recipes in cookbook
    for (Recipe recipe : recipes) {
      types.add(recipe.getType());
    }
    //Add types to the dropdown menu, if not duplicate
    for (String type : types) {
      if (!typeFilter.getItems().contains(type) && type != null) {
        typeFilter.getItems().add(type);
      }
    }
    //Set default value of dropdown
    typeFilter.setValue("All types");
  }

  public void filterByType() {
    resetPreferences();
    lactosefreeCheckBox.setSelected(false);
    //get value from dropdown
    String filterValue = (String) typeFilter.getValue();

    //fill cookbook with filtered recipes
    if (filterValue.equals("All types")) {
      fillCookbook(cookbook);
    } else {
      fillCookbook(cookbookAccess.filterByType(filterValue));
    }
    typeFilter.setValue(filterValue);
  }
  public void viewFavorites(ActionEvent e){
    resetPreferences();
    if (favoritesCheckBox.isSelected()){
      fillCookbook(cookbookAccess.filterByFavorite());
    }
    else {
      fillCookbook(cookbookAccess.fetchCookbook());
    }
  }

  public void viewPreferences(ActionEvent e){
    favoritesCheckBox.setSelected(false);
    String vlg = "FFF";
    if (veganCheckBox.isSelected()){
      vlg = "T" + vlg.substring(1);
    }
    if (lactosefreeCheckBox.isSelected()){
      vlg = vlg.substring(0,1) + "T" + vlg.substring(2);
    }
    if (glutenFreeCheckBox.isSelected()){
      vlg = vlg.substring(0,2) + "T";
    }
    if (vlg == "FFF"){
      fillCookbook(cookbookAccess.fetchCookbook());
    }
    fillCookbook(cookbookAccess.filterByPreferences(vlg));
  }
  

  

  //Switches from main scene to AddRecipe scene
  public void switchToAddRecipe(final ActionEvent event) throws IOException {
    // load AddRecipe
    FXMLLoader loader = new FXMLLoader(getClass().getResource("AddRecipe.fxml"));
    Parent root = loader.load();
    // update scene
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    // send the cookbookAccess to AddRecipeController
    AddRecipeController addRecipeController = loader.getController();
    addRecipeController.setCookbookAccess(this.cookbookAccess);
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
    System.out.println("Set recipe view controller cookbook access to " + cookbookAccess);
    viewController.setCookbookAccess(cookbookAccess);
    viewController.loadRecipe(sendRecipe);
  }  

  // remove recipe from cookbook
  public void removeRecipe(Recipe recipe) {
    if (cookbookAccess.removeRecipe(recipe.getName())) {
      fillCookbook(cookbookAccess.fetchCookbook());
      feedbackLabel.setText("Removed recipe");
    }
    else {
      feedbackLabel.setText("Could not remove recipe");
    }
  }

  public void setFeedbackLabel(String feedback) {
    feedbackLabel.setText(feedback);
  }

  public int getCookbookSize() {
    return cookbook.getRecipes().size();
  }

  public CookbookAccess getCookbookAccess() {
    return cookbookAccess;
  }

  public void setAccessType(boolean override) { // override = True always choose local cookbook.
    this.remote = false;
    try {
      URL url = new URL("http://localhost:8080/api/cookbook");
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      
      // Set request method, e.g., GET, POST, etc.
      connection.setRequestMethod("GET");

      // Connect to the URL
      connection.connect();

      // Check the response code
      int responseCode = connection.getResponseCode();
      if (responseCode == HttpURLConnection.HTTP_OK) {
          this.remote = true;
      }
      // Disconnect after checking
      connection.disconnect();
    } catch (IOException exception) {
      System.out.println("Error occured fetching cookbook");
    }

    if (remote && !override) {
      cookbookAccess = new RemoteCookbookAccess();
      System.out.println("Successfully fetched cookbook, using remote access");
    } else {
      cookbookAccess = new LocalCookbookAccess();
      System.out.println("Using local access");
    }
  }
  private void resetPreferences(){
    veganCheckBox.setSelected(false);
    glutenFreeCheckBox.setSelected(false);
    lactosefreeCheckBox.setSelected(false);
  }
}
