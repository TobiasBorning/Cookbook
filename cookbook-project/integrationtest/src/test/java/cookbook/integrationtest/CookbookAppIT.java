package cookbook.integrationtest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testfx.framework.junit5.ApplicationTest;

import com.google.gson.Gson;

import cookbook.accessdata.CookbookAccess;
import cookbook.core.Cookbook;
import cookbook.core.Recipe;
import cookbook.json.CookbookHandler;
import cookbook.ui.AppController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CookbookAppIT extends ApplicationTest{

  private Parent root;
  private AppController controller;
  private Scene scene;
  private CookbookAccess access;
  private Gson gson;
  private CookbookHandler ch = new CookbookHandler();
  private Cookbook savedCookbook;

  @BeforeAll
  public void saveCookbook() throws IOException {
    savedCookbook = ch.readFromFile("../persistence/remote-cookbook.json");
  }

  @AfterAll
  public void loadSavedCookbook() throws IOException {
    ch.writeToFile(savedCookbook, "../persistence/remote-cookbook.json");
  }

  private void setup() throws FileNotFoundException {
    gson = new Gson();
    Recipe recipe1 = new Recipe();
    recipe1.setName("Taco");
    Recipe recipe2 = new Recipe();
    recipe2.setName("Pizza");
    Recipe recipe3 = new Recipe();
    recipe3.setName("Pasta");
    Cookbook testCookbook = new Cookbook();
    testCookbook.addRecipe(recipe1);
    testCookbook.addRecipe(recipe2);
    testCookbook.addRecipe(recipe3);
    ch.writeToFile(testCookbook, "../persistence/remote-cookbook.json");
  }

  @Override
  public void start(Stage stage) throws IOException {
    setup();
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CookbookApp_it.fxml"));
    this.root = fxmlLoader.load();
    this.controller = fxmlLoader.getController();
    this.access = controller.getCookbookAccess();
    this.scene = new Scene(root);
    stage.setScene(this.scene);
    stage.show();
  }

  @Test
  public void allModulesReturnSameCookbook() {
    //Fetch cookbook from all different sources and check that they contain only Taco, Pizza and Pasta recipes
    assertEquals(new ArrayList<String>(Arrays.asList("Taco","Pizza","Pasta")), recipesFromCookbookAccess());
    assertEquals(recipesFromCookbookAccess(), recipesFromUI());
    assertEquals(recipesFromUI(), recipesFromSpringbootServer());
    assertEquals(recipesFromSpringbootServer(), recipesFromPersistence());
  }

  private List<String> recipesInCookbook(Cookbook cookbook) {
    return cookbook.getRecipes().stream().map(r -> r.getName()).toList();
  }

  private List<String> recipesFromPersistence() {
    try {
      return recipesInCookbook(ch.readFromFile("../persistence/remote-cookbook.json"));
    }
    catch (IOException e) {
      return null;
    }
  } 

  private List<String> recipesFromSpringbootServer() {
    try {
      URI uri = new URI("http://localhost:8080/api/");
      HttpRequest request = HttpRequest.newBuilder(uri.resolve("cookbook"))
          .header("Accept", "application/json")
          .GET()
          .build();
      
      HttpResponse<String> response = HttpClient.newBuilder()
          .build()
          .send(request, HttpResponse.BodyHandlers.ofString());
      Cookbook ut = gson.fromJson(response.body(), Cookbook.class);
      //System.out.println(ut.getRecipes().stream().map(Recipe::getName).toList());
      return recipesInCookbook(ut);
    } catch (Exception e) {
      throw new RuntimeException("Failed loading springboot cookbook");
    }
  }

  private List<String> recipesFromCookbookAccess() {
    return recipesInCookbook(access.fetchCookbook());
  }

  private List<String> recipesFromUI() {
    List<String> recipeNames = new ArrayList<String>();
    VBox recipeList = (VBox) root.lookup("#recipeList");
    for (Node node1 : recipeList.getChildren()) {
      Pane recipePane = (Pane) node1;
      for (Node node2 : recipePane.getChildren()) {
        if (node2 instanceof Label) {
          Label recipeName = (Label) node2;
          recipeNames.add(recipeName.getText());
        }
      }
    }
    return recipeNames;
  }
}
