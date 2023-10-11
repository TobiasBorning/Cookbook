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


    
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("CookbookApp.fxml"));
        this.root = fxmlLoader.load(); //endrer fra root til final Parent parent
        controller = fxmlLoader.getController();
        stage.setScene(new Scene(root)); // bytter ut root med parent
        stage.show();
    }


    @Test
    public void test() {
        System.out.println(getCookbookSize());
        System.out.println(getRecipeNames());
    }

    @Test
    public void testSearch(){
        searchRecipeFXRobot("Pasta Carbonara");
        assertEquals(1, getCookbookSize());
        //assertEquals(List.of("Pasta Carbonara"), getRecipeNames());

        searchRecipeFXRobot("unavaliable");
        assertEquals(0, getCookbookSize());
        //assertEquals(List.of(), getRecipeNames());

        searchRecipeFXRobot("");
        assertEquals(13, getCookbookSize());
    }

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
            }
        }
        return utlist;
    }

    private boolean containsRecipe(String recipe) {
        return getRecipeNames().stream().map(s -> s.toLowerCase()).toList().contains(recipe.toLowerCase());
    }











    // public Parent getRootNode() {
    //     return root;
    // }

    private String enterLabel = """
        E
        n
        t
        e
        r
        """.stripTrailing();

    private void click(String... labels) {
        for (var label : labels) {
            clickOn(LabeledMatchers.hasText(label));
        }
    }

    // private static Stream<String> searchTexts(){
    //     return Stream.of(
    //         "pasta",
    //         "unavaliable", 
    //         ""
    //     );
    // }

    // private void clickButton(String id){
    //     Node button = lookup(id).query();
    //     clickOn(null, null, null);
    //     if (button instanceof Button){
    //         FxRobot fxRobot = new FxRobot();
    //         fxRobot.clickOn(button, MouseButton.PRIMARY);
    //     } else{
    //         throw new IllegalArgumentException("Button not found");
    //     }
    // }

    //legger basert på individuell øving

    private String getSearchString() {
        TextField search = (TextField) root.lookup("#searchField");
        return search.getText();
    }

    // forsøk på tester:

    // @ParameterizedTest
    // @MethodSource("searchTexts")
    // public void testSearch(String searchFieldText) {
    //     Scene scene = getScene();
    //     ScrollPane scrollPane = (ScrollPane) scene.lookup("recipeList");
    //     int initialScrollPaneSize = ((VBox)scrollPane.getContent()).getChildren().size(); 
        
    //     if search field is empty, check that the scrollpane recipeList does not change contents
    //     if something is written, all matching recipes search is displayed in scrollpane
    //     if no matches, scrollpane does not change
        
    // }

    
    public void testSearching(){
        Scene testScene = new Scene(new Group());
        VBox vBox = (VBox)testScene.lookup("recipeList");

        searchRecipeFXRobot("pasta"); //må bytte til VBOX
        assertEquals(1, (vBox.getChildren().size()));
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
