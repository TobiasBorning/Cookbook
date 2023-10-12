package cookbook.ui;

import javafx.fxml.FXMLLoader;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

/**
 * TestFX App test
 */
public class CookbookAppTest extends ApplicationTest {

    private AppController controller;
    private Parent root;
    private Scene scene;
    private int loadedCookbookSize = 0;
    
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("CookbookApp.fxml"));
        this.root = fxmlLoader.load(); //endrer fra root til final Parent parent
        controller = fxmlLoader.getController();
        this.scene = new Scene(root);
        stage.setScene(this.scene); // bytter ut root med parent
        stage.show();
    }

    @BeforeEach
    public void setLoadedCookbookSize() {
        if (this.loadedCookbookSize == 0) {
            this.loadedCookbookSize = controller.getCookbookSize();
        }
    }    

    @Test
    public void testSearch(){
        // controller.fillDefaultCookbook();
        searchRecipe("Pasta Carbonara");
        assertTrue(containsRecipe("Pasta Carbonara"));
        assertEquals(1, getCookbookSize());
        assertEquals(List.of("Pasta Carbonara"), getRecipeNames());
    
        searchRecipe("");
        sleep(1000);
        assertEquals(loadedCookbookSize, getCookbookSize());

        searchRecipe("unavaliable");
        assertFalse(containsRecipe("unavaliable"));
        assertEquals(loadedCookbookSize, getCookbookSize());
        String feedbackLabelText = ((Labeled)lookup("#feedbackLabel").query()).getText();
        assertEquals("No recipes matching search", feedbackLabelText);
    }

    @Test
    public void testViewClick() {
        //make sure nachos is viewable
        searchRecipe("nachos");
        //click on view button
        clickOn("#viewnachos");

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
        assertEquals("nachos", title.getText());

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
        assertEquals(List.of("Veggie Wrap", "Oatmeal"), getRecipeNames());
    }

    @Test
    public void addAndRemoveRecipeTest() {
        // navigate to add recipe scene
        clickOn("#addRecipeButton");
        sleep(1000);

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
        clickOn("#addRecipeDescription").write("Water is good ad super healty");
        clickOn("#addRecipeOrigin").write("Norway");
        //add ingredient 2
        clickOn("#addIngredientButton");
        clickOn("#ingredientName2").write("salt");
        clickOn("#ingredientAmount2").write("10g");
        // add recipe to cookbook
        clickOn("#addRecipeButton");
        
        //Check that the recipe is added
        viewAllRecipes();
        assertTrue(containsRecipe("Water"));

        //Remove the recipe, needs search to view the button without scrolling
        searchRecipe("Water");
        clickOn("#removeWater");
        String feedbackLabelText = ((Labeled)lookup("#feedbackLabel").query()).getText();
        assertEquals("Removed recipe", feedbackLabelText);

        //Check that the recipe is removed
        viewAllRecipes();
        assertEquals(loadedCookbookSize, getCookbookSize());
        assertFalse(containsRecipe("Water"));
        
    }

    // returns items viewable in the VBox
    private int getCookbookSize() {
        VBox list = (VBox) lookup("#recipeList").query();
        return list.getChildren().size();
    }

    // returns a list of the names of the recipes viewable in the VBox
    private List<String> getRecipeNames() {
        List<String> utlist = new ArrayList<>();
        VBox list = (VBox) lookup("#recipeList").query();
        for (Node node : list.getChildren()) {
            Pane pane = (Pane) node;
            for (Node child : pane.getChildren()) {
                if (child instanceof Label) {
                    Label label = (Label) child;
                    utlist.add(label.getText());
                }
            }
        }
        return utlist;
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
        searchRecipe("");
    }

}
