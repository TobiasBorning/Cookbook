package cookbook.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;

import cookbook.ui.AppController;

/**
 * TestFX App test
 */
public class CookbookAppTest extends ApplicationTest {

    private AppController controller;
    private Parent root;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("CookbookApp.fxml"));
        root = fxmlLoader.load();
        controller = fxmlLoader.getController();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public Parent getRootNode() {
        return root;
    }

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

    //legger basert på individuell øving
    //trengs kanskje ikke

    private String getSearchString() {
        TextField search = (TextField) root.lookup("#searchField");
        return search.getText();
    }

    // private boolean searchPressed() {

    // }

    // forsøk på tester:

    @ParameterizedTest
    @MethodSource
    public void testSearch(String searchFieldText) {
        //hvis søket er tomt - sjekk at viewet ikke endrer seg
        //dersom det er står noe, og det samsvarer, sjekk at det kommer i view
        //dersom ingen matcher - sjekk at alle blir og du får beskjed om feil
        
    }

    
}
