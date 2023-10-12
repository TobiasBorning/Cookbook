package cookbook.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane; //newly added
import javafx.stage.Stage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.api.FxRobot; //newly added

import cookbook.ui.AppController;

/**
 * TestFX App test
 */
public class CookbookAppTest extends ApplicationTest {

    private AppController controller;
    private Parent root;
    private Scene scene;

    // @Before
    // public void setUp(){
    //     controller.fillDefaultCookbook();
    // }
    
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("CookbookApp.fxml"));
        this.root = fxmlLoader.load(); //endrer fra root til final Parent parent
        controller = fxmlLoader.getController();
        controller.fillDefaultCookbook();
        stage.setScene(new Scene(root)); // bytter ut root med parent
        stage.show();
    }

    @Test
    public void test() {
        //controller.fillDefaultCookbook();
        System.out.println(getCookbookSize());
        System.out.println(getRecipeNames());
    }
    
    

    @Test
    public void testSearch(){
        // controller.fillDefaultCookbook();
        searchRecipeFXRobot("Pasta Carbonara");
        assertTrue(containsRecipe("Pasta Carbonara"));
        assertEquals(1, getCookbookSize());
        assertEquals(List.of("Pasta Carbonara"), getRecipeNames());
        clearSearchInput();

        searchRecipeFXRobot("");
        assertEquals(15, getCookbookSize());

        searchRecipeFXRobot("unavaliable");
        assertFalse(containsRecipe("unavaliable"));
        assertEquals(15, getCookbookSize());
        //assertEquals(List.of(), getRecipeNames());
    }

    // @Test
    // public void testViewClick(FxRobot robot) {
    //     robot.clickOn("#viewPizza");
    //     sleep(1000);
    //     Parent viewRoot = robot.lookup("#recipeViewRoot").query();
    //     assertEquals("#recipeViewRoot", viewRoot.getId());
    // }

    // @Test
    // public void testRemove(){
    //     System.out.println("Før: " + getCookbookSize());
    //     clickOn("#removePizza");
    //     sleep(1000);
    //     System.out.println("etter: " + getCookbookSize());
    //     assertEquals(14, getCookbookSize());
    //     assertFalse(containsRecipe("Pizza"));
    //     //controller.fillDefaultCookbook();
    // }

    private int getCookbookSize() {
        VBox list = (VBox) root.lookup("#recipeList");
        return list.getChildren().size();
    }

    private List<String> getRecipeNames() {
        List<String> utlist = new ArrayList<>();
        VBox list = (VBox) root.lookup("#recipeList");
        for (Node node : list.getChildren()) {
            Pane pane = (Pane) node;
            for (Node child : pane.getChildren()) {
                if (child instanceof Label) {
                    Label label = (Label) child;
                    utlist.add(label.getText());
                }
                //System.out.println(child.getId());
            }
        }
        return utlist;
    }

    private boolean containsRecipe(String recipe) {
        return getRecipeNames().stream().map(s -> s.toLowerCase()).toList().contains(recipe.toLowerCase());
    }


    private String getSearchString() {
        TextField searchField = (TextField) root.lookup("#searchField");
        return searchField.getText();
    }

    private void clearSearchInput(){
        TextField searchField = (TextField) root.lookup("#searchField");
        searchField.clear();
    }

    private void searchRecipeFXRobot(String name){
        clickOn("#searchField").write(name);
        clickOn("#searchButton");
    }

    private void removeRecipeFXRobot(){
        //hvordan nå removeknappen på en spesifikk oppskrift?
    }
//scrollpane har children, kan feks streame for å sjekke om det matcher search

}
