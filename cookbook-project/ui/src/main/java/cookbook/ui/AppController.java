package cookbook.ui;

import cookbook.accessdata.CookbookAccess;
import cookbook.accessdata.LocalCookbookAccess;
import cookbook.accessdata.RemoteCookbookAccess;
import cookbook.core.Cookbook;
import cookbook.core.Recipe;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * Controller class for the main application view.
 *<p>
 * This class is responsible for handling user interactions with the main application window.
 * It manages the display and manipulation of recipes within the cookbook, including searching,
 * filtering, and viewing individual recipes. It also handles navigation to other views such as
 * adding or viewing a specific recipe.
 *</p>
 *<p>
 * The controller initializes with the primary stage and scene of the application, setting up
 * the necessary bindings and event handlers. It also determines the access type for the cookbook,
 * choosing between local and remote access based on the availability of a network connection.
 *</p>
 */
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
  private ChoiceBox<String> originFilter;
  @FXML
  private Button filterByOrigin;
  @FXML
  private Button allRecipesButton;
  @FXML
  private ChoiceBox<String> typeFilter;
  @FXML
  private Button filterByType;
  @FXML
  private CheckBox favoritesCheckBox;
  @FXML
  private CheckBox veganCheckBox;
  @FXML
  private CheckBox lactoseFreeCheckBox;
  @FXML
  private CheckBox glutenFreeCheckBox;

  /**
   * Initializes the controller class.
   *<p>
   * This method sets up the initial state of the application's main view by determining the
   * access type for the cookbook (remote or local) and fetching the initial set of recipes.
   * It adjusts the UI components, such as setting the minimum height of the recipe list VBox
   * to accommodate all the recipes, and populates the view with the fetched recipes.
   *</p>
   *<p>
   * The method is called automatically after the FXML fields have been populated and is typically
   * used to perform any necessary setup or initialization tasks for the controller
   *</p>
   */
  public void initialize() {
    // set remote or local cookbook access depending on connection, override 
    // == true always connects to local
    setAccessType(false);
    this.cookbook = cookbookAccess.fetchCookbook();
    // set Vbox height to fit all recipes
    recipeList.setMinHeight(this.cookbook.getRecipes().size() * 69.5);
    fillCookbook(this.cookbook);
  }

  /**
   * Populates the recipe list view with recipe entries from the provided cookbook.
   * Each recipe is displayed with options to view, remove, and toggle favorite status.
   *
   * @param cookbook The cookbook containing recipes to display in the list.
   */
  private void fillCookbook(Cookbook loadCookbook) {
    recipeList.getChildren().clear();
    Collection<Recipe> cookbooklist = loadCookbook.getRecipes();
    if (!cookbooklist.isEmpty()) {
      feedbackLabel.setText("");
    }
    for (Recipe recipe : cookbooklist) {
      //creates pane to display recipe
      Pane pane = new Pane();
      pane.setMinWidth(330);
      pane.setMaxWidth(400);
      pane.setMinHeight(70);
      pane.setStyle("-fx-padding: 10 10 10 10;" 
          + "-fx-border-width: 0px 0px 3px 0px; -fx-border-color: #000000;");

      //heading for recipe name
      Label recipeName = new Label(recipe.getName());
      //set label CSS id for testing
      recipeName.setId(recipe.getName() + "Recipe");
      Font font = Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 16);
      recipeName.setFont(font);
      recipeName.setMaxWidth(200);
      recipeName.setLayoutX(10);
      recipeName.setLayoutY(10);

      // Add remove button
      Button buttonRemove = new Button("Remove");
      //set button CSS id for testing
      buttonRemove.setId("remove" + recipe.getName()); //ex: #removeTaco
      // Adjust the x-coordinate as needed
      buttonRemove.setLayoutX(pane.getMinWidth() - buttonRemove.getMinWidth());
      buttonRemove.setLayoutY(10); // Adjust the y-coordinate as needed
      buttonRemove.onActionProperty().set(e -> {
        removeRecipe(recipe);
      });
    
      // Add view button
      Button buttonView = new Button("View");
      //set button CSS id for testing
      buttonView.setId("view" + recipe.getName()); //ex: #viewTaco

      // Adjust the x-coordinate as needed
      buttonView.setLayoutX(pane.getMinWidth() - buttonView.getMinWidth()
           - buttonRemove.getMinWidth() - 50);
      
      buttonView.setLayoutY(10); // Adjust the y-coordinate as needed
      buttonView.onActionProperty().set(e -> {
        try {
          this.sendRecipe = recipe;
          switchToViewRecipe(e);
        } catch (IOException ex) {
          System.err.println(ex);
        }
      });

      //Add favorite button
      Button buttonFavorite = new Button("★");
      if (recipe.getFavorite()) {
        buttonFavorite.setStyle("-fx-background-color: yellow;");
      } else {
        buttonFavorite.setStyle("");
      }
      buttonFavorite.setId("favorite" + recipe.getName()); //ex: #removeTaco

      // Adjust the x-coordinate as needed
      buttonFavorite.setLayoutX(pane.getMinWidth() - buttonView.getMinWidth() 
          - buttonRemove.getMinWidth()  - 87.5 - buttonFavorite.getMinWidth()); 

      // Adjust the y-coordinate as needed
      buttonFavorite.setLayoutY(10); 

      buttonFavorite.onActionProperty().set(e -> {
        cookbookAccess.toggleFavorite(recipe);
        fillCookbook(cookbookAccess.fetchCookbook());
        favoritesCheckBox.setSelected(false);
      });
      pane.getChildren().addAll(recipeName, buttonView, buttonRemove, buttonFavorite);
      recipeList.getChildren().add(pane);
    }

    //Fill filter dropdown menu with origins from cookbook
    fillOriginFilterDropdown();
    //Fill filter dropdown menu with types from cookbook
    fillTypeFilterDropdown();
  }

  /**
   * Executes a search based on the text entered in the search field.
   * If the search field is empty, all recipes are displayed.
   * If there are no matches for the search, a message is displayed.
   * If there are matches, the recipe list is updated to show only those recipes.
   */
  public void search() {
    resetPreferences();
    try {
      String search = searchField.getText();
      if (search.isEmpty()) {
        fillCookbook(cookbookAccess.fetchCookbook());
        feedbackLabel.setText("");
      } else {
        Cookbook searched = cookbookAccess.searchRecipe(search);
        if (searched == null || searched.getRecipes().isEmpty()) {
          feedbackLabel.setText("No recipes matching search");
        } else {
          fillCookbook(searched);
          feedbackLabel.setText("");
        }
      }
    } catch (RuntimeException e) {
      feedbackLabel.setText("No recipes matching search");
      fillCookbook(new Cookbook());
    }
  }

  /**
   * Populates the filter dropdown menu with unique origin countries from the recipes.
   * It adds an "All origins" option to allow users to remove the filter.
   */
  private void fillOriginFilterDropdown() {
    Set<String> origins = new HashSet<>();
    Collection<Recipe> recipes = cookbook.getRecipes();

    origins.add("All origins");

    for (Recipe recipe : recipes) {
      origins.add(recipe.getOriginCountry());
    }
    for (String origin : origins) {
      if (!originFilter.getItems().contains(origin) && origin != null) {
        originFilter.getItems().add(origin);
      }
    }
    originFilter.setValue("All origins");
  }

  /**
   * Filters the displayed recipes by the selected country of origin.
   * It deselects any active preferences and resets the filter if "All origins" is selected.
   */
  public void filterByOrigin() {
    try {
      favoritesCheckBox.setSelected(false);
      resetPreferences();
      String filterValue = (String) originFilter.getValue();

      if (filterValue.equals("All origins")) {
        fillCookbook(cookbook);
      } else {
        fillCookbook(cookbookAccess.filterByOrigin(filterValue));
      }
      originFilter.setValue(filterValue);
    } catch (RuntimeException e) {
      feedbackLabel.setText("No recipes matching the origin");
      fillCookbook(new Cookbook());
    }
  }

  private void fillTypeFilterDropdown() {
    Set<String> validTypes = new HashSet<>(Arrays.asList(null, "Breakfast", "Lunch",
        "Dinner", "Dessert", "Unknown", "All types"));
    
    for (String type : validTypes) {
      if (!typeFilter.getItems().contains(type) && type != null) {
        typeFilter.getItems().add(type);
      }
    }

    typeFilter.setValue("All types");
  }

  /**
   * Populates the type filter dropdown with unique recipe types from the cookbook.
   * It adds an option for no filter and sets the default filter value to "All types".
   */
  public void filterByType() {
    try {
      resetPreferences();
      String filterValue = (String) typeFilter.getValue();

      if (filterValue.equals("All types")) {
        fillCookbook(cookbook);
      } else {
        fillCookbook(cookbookAccess.filterByType(filterValue));
      }
      typeFilter.setValue(filterValue);
    } catch (RuntimeException e) {
      feedbackLabel.setText("No recipes matching the type");
      fillCookbook(new Cookbook());
    }
  }

  /**
   * Handles the action of viewing favorite recipes. If the favorites checkbox is selected, 
   * it displays only the favorite recipes; otherwise, it displays all recipes.
   *
   * @param event the ActionEvent that triggered this method
   */
  public void viewFavorites(final ActionEvent event) {
    try {
      resetPreferences();
      if (favoritesCheckBox.isSelected()) {
        fillCookbook(cookbookAccess.filterByFavorite());
      } else {
        fillCookbook(cookbookAccess.fetchCookbook());
      }
    } catch (RuntimeException e) {
      feedbackLabel.setText("No recipes matching the favorites");
      fillCookbook(new Cookbook());
    }
  }

  /**
   * Filters the displayed recipes based on selected dietary preferences.
   * Unchecks the favorites checkbox and updates the cookbook view with recipes
   * that match the selected vegan, lactose-free, and gluten-free options.
   *
   * @param event the action event that triggered the method
   */
  public void viewPreferences(final ActionEvent event) {
    try {
      favoritesCheckBox.setSelected(false);
      String vlg = "FFF";
      if (veganCheckBox.isSelected()) {
        vlg = "T" + vlg.substring(1);
      }
      if (lactoseFreeCheckBox.isSelected()) {
        vlg = vlg.substring(0, 1) + "T" + vlg.substring(2);
      }
      if (glutenFreeCheckBox.isSelected()) {
        vlg = vlg.substring(0, 2) + "T";
      }
      if (vlg == "FFF") {
        fillCookbook(cookbookAccess.fetchCookbook());
      }
      fillCookbook(cookbookAccess.filterByPreferences(vlg));
    } catch (RuntimeException e) {
      feedbackLabel.setText("No recipes matching the preferences");
      fillCookbook(new Cookbook());
    }
  }
  
  /**
   * Switches the current view to the "Add Recipe" scene.
   * This method loads the AddRecipe.fxml layout, sets the scene, and passes the current
   * cookbook access to the AddRecipeController.
   *
   * @param event the action event that triggered the switch
   * @throws IOException if the FXML file cannot be loaded
   */
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

  /**
   * Switches the current view to the "View Recipe" scene.
   * This method loads the RecipeView.fxml layout, sets the scene, and initializes the
   * RecipeViewController with the selected recipe and cookbook access.
   *
   * @param event the action event that triggered the switch
   * @throws IOException if the FXML file cannot be loaded
   */
  public void switchToViewRecipe(final ActionEvent event) throws IOException {
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
    System.out.println("Set recipe view controller cookbook access to " + cookbookAccess);
    viewController.setCookbookAccess(cookbookAccess);
    viewController.loadRecipe(sendRecipe);
  }  

  /**
   * Removes a specified recipe from the cookbook.
   * If the recipe is successfully removed, the cookbook is refreshed to reflect the change.
   * Feedback is provided to the user through the feedbackLabel.
   *
   * @param recipe the recipe to be removed
   */
  public void removeRecipe(Recipe recipe) {
    try {
      if (cookbookAccess.removeRecipe(recipe.getName())) {
        fillCookbook(cookbookAccess.fetchCookbook());
        feedbackLabel.setText("Removed recipe");
      } else {
        feedbackLabel.setText("Could not remove recipe");
      }
    } catch (RuntimeException e) {
      feedbackLabel.setText("Could not remove recipe");
    }
  }

  /**
   * Sets the text of the feedback label to the specified message.
   *
   * @param feedback the message to display on the feedback label
   */
  public void setFeedbackLabel(String feedback) {
    feedbackLabel.setText(feedback);
  }

  /**
   * Retrieves the number of recipes in the cookbook.
   *
   * @return the size of the cookbook as an integer
   */
  public int getCookbookSize() {
    return cookbook.getRecipes().size();
  }

  /**
   * Provides access to the current CookbookAccess instance.
   *
   * @return the CookbookAccess instance being used by the controller
   */
  public CookbookAccess getCookbookAccess() {
    return cookbookAccess;
  }

  /**
   * Configures the type of CookbookAccess (local or remote) based on the availability of the 
   * remote service. If the remote service is available and the override flag is not set, remote 
   * access is used. Otherwise, local access is used. The override flag forces the use of local 
   * access when set to true.
   *
   * @param override if true, forces the use of local cookbook access regardless of remote
   *      service availability
   */
  public void setAccessType(boolean override) {
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
      System.out.println("Error occured fetching remote cookbook");
    }
    try {
      if (remote && !override) {
        cookbookAccess = new RemoteCookbookAccess();
        System.out.println("Successfully fetched cookbook, using remote access");
      } else {
        cookbookAccess = new LocalCookbookAccess();
        System.out.println("Using local access");
      }
    } catch (RuntimeException e) {
      feedbackLabel.setText("Error occured loading or creating cookbook");
    }
  }
  
  /**
   * Resets the selection states of dietary preference checkboxes to their default unselected state.
   */
  private void resetPreferences() {
    veganCheckBox.setSelected(false);
    glutenFreeCheckBox.setSelected(false);
    lactoseFreeCheckBox.setSelected(false);
  }
}
