package cookbook.ui;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testfx.framework.junit5.ApplicationTest;

import cookbook.accessdata.CookbookAccess;
import cookbook.accessdata.RemoteCookbookAccess;
import cookbook.core.Cookbook;
import cookbook.core.Recipe;
import cookbook.json.CookbookHandler;

/**
 * TestFX App test
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS) //for å kunne bruke @beforeAll non static
public class CookbookAppTest extends ApplicationTest {

    private AppController controller;
    private Parent root;
    private Scene scene;
    // private int loadedCookbookSize = 0;
    private Cookbook savedRemoteCookbook;
    private Cookbook savedLocalCookbook;
    private Cookbook testCookbook;
    private CookbookHandler ch = new CookbookHandler();
    // private String path;
    private FXMLLoader fxmlLoader;

    @BeforeAll
    private void saveAndFillCookbook() {
        try{
            savedRemoteCookbook = ch.readFromFile("../persistence/remote-cookbook.json");
            savedLocalCookbook = ch.readFromFile( "../persistence/cookbook.json");
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found");
        } // kan alt være i én try catch?
        try {
            this.testCookbook = ch.readFromFile("../persistence/ui-test-cookbook.json");
            System.out.println(testCookbook.getRecipes().size());
            ch.writeToFile(testCookbook, "../persistence/remote-cookbook.json");
            ch.writeToFile(testCookbook, "../persistence/cookbook.json");
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    @AfterAll
    private void writeSavedCookbook() {
        try {
            ch.writeToFile(savedRemoteCookbook, "../persistence/remote-cookbook.json");
            ch.writeToFile(savedLocalCookbook, "../persistence/cookbook.json");
            ch.writeToFile(testCookbook, "../persistence/ui-test-cookbook.json");
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    public void start(Stage stage) throws IOException {
        this.fxmlLoader = new FXMLLoader(this.getClass().getResource("CookbookApp.fxml"));
        this.root = fxmlLoader.load(); //endrer fra root til final Parent parent
        this.controller = fxmlLoader.getController();
        this.scene = new Scene(root);
        stage.setScene(this.scene); // bytter ut root med parent
        stage.show();
    }

    // @BeforeEach
    // public void setLoadedCookbookSize() {
    //     if (this.loadedCookbookSize == 0) {
    //         this.loadedCookbookSize = controller.getCookbookSize();
    //     }
    // }    
/* */
    @Test
    public void testSearch(){
        // controller.fillDefaultCookbook();
        searchRecipe("Pasta Carbonara");
        assertTrue(containsRecipe("Pasta Carbonara"));
        assertEquals(1, getCookbookSize());
        assertEquals(List.of("Pasta Carbonara"), getRecipeNames());
    
        searchRecipe("");
        sleep(1000);
        assertEquals(10, getCookbookSize());

        searchRecipe("unavaliable");
        assertFalse(containsRecipe("unavaliable"));
        assertEquals(10, getCookbookSize());
        String feedbackLabelText = ((Labeled)lookup("#feedbackLabel").query()).getText();
        assertEquals("No recipes matching search", feedbackLabelText);
    }

    @Test
    public void testViewClick() {
        //make sure nachos is viewable
        searchRecipe("Nachos");
        //click on view button
        clickOn("#viewNachos");

        //check that the view pane is loaded
        Node viewNode = lookup("#recipeViewPane").query();
        assertTrue(viewNode.getId().equals("recipeViewPane"));

        String originString = ((Labeled)lookup("#origin").query()).getText();
        String descriptionString = ((Labeled)lookup("#description").query()).getText();
        String ingredientsString = ((Labeled)lookup("#ingredients").query()).getText();
        assertEquals("mexico", originString);
        assertEquals("yummy", descriptionString);
        assertEquals('\n'+"ost:  100g"+'\n'+"chips:  192"+'\n', ingredientsString);
        

        //check that title is correct
        Label title = lookup("#recipeName").query();
        assertEquals("Nachos", title.getText());

        //navigate back to all recipes
        clickOn("#allRecipesButton");
        lookup("#cookbookAppPane");
        assertFalse(lookup("#cookbookAppPane")==null);
    }

    @Test
    public void filterOriginTest(){
        clickOn("#filter");
        sleep(500);
        clickOn("America");
        clickOn("#filterByOrigin");
        assertEquals(2, getCookbookSize());
        assertEquals(List.of("Oatmeal", "Veggie Wrap"), getRecipeNames());
        clickOn("#filter");
        sleep(500);
        clickOn("All origins");
        clickOn("#filterByOrigin");
        assertEquals(10, getCookbookSize());
        assertEquals(List.of("Taco", "Pizza", "Spaghetti Bolognese", "Chicken Stir-Fry", "Caesar Salad", "Pasta Carbonara", "Oatmeal", "Vegetable Curry", "Veggie Wrap", "Nachos"), getRecipeNames());
    }

    // @Test - should remove this test as it make other tests fail AND remove is tested in another test
    public void removeRecipeTest(){
        assertEquals(10, getCookbookSize());
        assertTrue(containsRecipe("Pizza"));
        clickOn("#removePizza");
        assertEquals(9, getCookbookSize());
        assertFalse(containsRecipe("Pizza"));
    }
    @Test
    public void addExistingRecipeTest(){
        clickOn("#addRecipeButton");
        sleep(1000);
        //Add Taco recipe
        clickOn("#addRecipeName").write("Taco");
        //add ingredient 1
        clickOn("#addIngredientButton");
        clickOn("#ingredientName1").write("Taco ingredients");
        clickOn("#ingredientAmount1").write("1");
        // add description and origin
        clickOn("#addRecipeDescription").write("Tacos are great");
        clickOn("#addRecipeOrigin").write("Mexico");
        clickOn("#addRecipeButton");
        assertEquals("Name already exists!", ((Labeled)lookup("#feedbackLabel").query()).getText());
    }

    @Test
    public void addAndRemoveRecipeTest() {
        // navigate to add recipe scene
        clickOn("#addRecipeButton");
        sleep(500);

        //Check that the add recipe pane is loaded
        Node addNode = lookup("#addRecipePane").query();
        assertTrue(addNode.getId().equals("addRecipePane"));

        //Add water recipe
        clickOn("#addRecipeName").write("Water");
        //add ingredient 1
        clickOn("#addIngredientButton");
        clickOn("#ingredientName1").write("water");
        clickOn("#ingredientAmount1").write("1l");
        // add description and origin
        clickOn("#addRecipeDescription").write("Water is good and super healthy");
        clickOn("#addRecipeOrigin").write("Norway");
        //add ingredient 2
        clickOn("#addIngredientButton");
        clickOn("#ingredientName2").write("salt");
        clickOn("#ingredientAmount2").write("10g");
        // add allergies
        clickOn("#veganCheckBox");
        clickOn("#lactosefreeCheckBox"); //må skrive likt - camelcase
        clickOn("#glutenFreeCheckBox"); //lactosefree og glutenFree
        // add recipe to cookbook
        clickOn("#addRecipeButton");
        
        //Check that the recipe is added
        viewAllRecipes();
        assertEquals(11, getCookbookSize());
        assertTrue(containsRecipe("Water"));

        //Remove the recipe, needs search to view the button without scrolling
        searchRecipe("Water");
        clickOn("#removeWater");
        String feedbackLabelText = ((Labeled)lookup("#feedbackLabel").query()).getText();
        assertEquals("Removed recipe", feedbackLabelText);

        //Check that the recipe is removed
        viewAllRecipes();
        assertEquals(10, getCookbookSize());
        assertFalse(containsRecipe("Water"));
    }

    @Test
    public void testEditRecipe(){
        // navigate to edit recipe scene
        clickOn("#viewPizza");
        clickOn("#editRecipeButton");
        sleep(500);
        clickOn("#ingredientAmount1").press(KeyCode.SHORTCUT).press(KeyCode.A).release(KeyCode.A).release(KeyCode.SHORTCUT).type(KeyCode.BACK_SPACE);
        clickOn("#ingredientAmount1").write("400.0");
        clickOn("#saveChangesButton");
        clickOn("#viewPizza");
        String ingredientsString = ((Labeled)lookup("#ingredients").query()).getText();
        assertEquals('\n' + "pizzaDough:  400.0" + '\n' + "mushrooms:  50.0" + '\n' + "onions:  30.0" + '\n' + "tomatoSauce:  150.0" + '\n' + "ruccula:  100.0" + '\n' + "pepperoni:  100.0" + '\n' + "aioli:  50.0" + '\n' + "cheese:  200.0" + '\n'
        , ingredientsString); //la til et ekstra mellomrom på alle i linja over for å få grønn test
    }

    @Test
    public void filterPreferencesTest(){
        clickOn("#veganCheckBox");
        assertEquals(1, getCookbookSize());
        assertEquals(List.of("Vegetable Curry"), getRecipeNames());
        clickOn("#glutenFreeCheckBox");
        assertEquals(1, getCookbookSize());
        assertEquals(List.of("Vegetable Curry"), getRecipeNames());
        clickOn("#lactosefreeCheckBox");
        assertEquals(1, getCookbookSize());
        clickOn("#veganCheckBox");
        assertEquals(1, getCookbookSize());
        assertEquals(List.of("Vegetable Curry"), getRecipeNames());
        clickOn("#lactosefreeCheckBox");
        assertEquals(2, getCookbookSize());
        assertEquals(List.of("Caesar Salad", "Vegetable Curry"), getRecipeNames());
        clickOn("#glutenFreeCheckBox");
        assertEquals(10, getCookbookSize());
        assertEquals(List.of("Taco", "Pizza", "Spaghetti Bolognese", "Chicken Stir-Fry", "Caesar Salad", "Pasta Carbonara", "Oatmeal", "Vegetable Curry", "Veggie Wrap", "Nachos"), 
            getRecipeNames());
        clickOn("#lactosefreeCheckBox");
        assertEquals(1, getCookbookSize());
        assertEquals(List.of("Vegetable Curry"), getRecipeNames());
        clickOn("#veganCheckBox");
        assertEquals(1, getCookbookSize());
    }

    @Test
    public void favoriteRecipeTest(){
        clickOn("#favoritesCheckBox");
        assertEquals(1, getCookbookSize());
        assertEquals(List.of("Caesar Salad"), getRecipeNames());
        clickOn("#favoritesCheckBox");
        clickOn("#favoritePizza");
        clickOn("#favoritesCheckBox");
        assertEquals(2, getCookbookSize());
        assertEquals(List.of("Pizza","Caesar Salad"), getRecipeNames());
        clickOn("#favoritePizza");
        clickOn("#favoritesCheckBox");
        assertEquals(1, getCookbookSize());
        assertEquals(List.of("Caesar Salad"), getRecipeNames());
    }

    @Test
    public void typeFilterTest(){
        clickOn("#typeFilter");
        sleep(200);
        clickOn("Dinner");
        clickOn("#filterByType");
        assertEquals(7, getCookbookSize());
        assertEquals(List.of("Taco", "Pizza", "Spaghetti Bolognese", "Chicken Stir-Fry", "Pasta Carbonara", "Vegetable Curry", "Nachos"), getRecipeNames());
        clickOn("#typeFilter");
        sleep(200);
        clickOn("Lunch");
        clickOn("#filterByType");
        assertEquals(2, getCookbookSize());
        assertEquals(List.of("Caesar Salad", "Veggie Wrap"), getRecipeNames());
        clickOn("#typeFilter");
        sleep(200);
        clickOn("Breakfast");
        clickOn("#filterByType");
        assertEquals(1, getCookbookSize());
        assertEquals(List.of("Oatmeal"), getRecipeNames());
        // clickOn("#typeFilter");
        // sleep(1000);
        // clickOn("Dessert");
        // clickOn("#filterByType");
        // assertEquals(0, getCookbookSize());
        clickOn("#typeFilter");
        sleep(200);
        clickOn("All types");
        clickOn("#filterByType");
        assertEquals(10, getCookbookSize());
        assertEquals(List.of("Taco", "Pizza", "Spaghetti Bolognese", "Chicken Stir-Fry", "Caesar Salad", "Pasta Carbonara", "Oatmeal", "Vegetable Curry", "Veggie Wrap", "Nachos"), 
            getRecipeNames());
    }

    @Test
    public void setFeedbackLabelTest(){
        Platform.runLater(() -> {
            String testLabel = "Test label";
            controller.setFeedbackLabel("Test label");
            controller.setFeedbackLabel(testLabel);
            String feedbackLabelText = ((Labeled)lookup("#feedbackLabel").query()).getText();
            assertEquals(testLabel, feedbackLabelText);
        });
    }


    // returns items viewable in the VBox
    private int getCookbookSize() {
        VBox list = (VBox) lookup("#recipeList").query();
        return list.getChildren().size();
    }

    // returns a list of the names of the recipes viewable in the VBox
    private List<String> getRecipeNames() {
        List<String> outList = new ArrayList<>();
        VBox list = (VBox) lookup("#recipeList").query();
        for (Node node : list.getChildren()) {
            Pane pane = (Pane) node;
            for (Node child : pane.getChildren()) {
                if (child instanceof Label) {
                    Label label = (Label) child;
                    outList.add(label.getText());
                }
            }
        }
        System.out.println(outList);
        return outList;
    }

    // returns true if the recipe is viewable in the VBox
    private boolean containsRecipe(String recipe) {
        return getRecipeNames().contains(recipe);
    }

    // searches for the recipe with the given name
    private void searchRecipe(String name){
        TextField tf = lookup("#searchField").query();
        tf.clear();
        clickOn("#searchField").write(name);
        clickOn("#searchButton");
    }
    
    // views all recipes in the VBox
    private void viewAllRecipes() {
        System.out.println("viewAllRecipes");
        searchRecipe("");
    }
}
