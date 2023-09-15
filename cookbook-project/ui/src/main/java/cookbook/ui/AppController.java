package cookbook.ui;

import java.util.Map.Entry;

import cookbook.core.Cookbook;
import cookbook.core.Recipe;
import cookbook.json.CookbookHandler;
import javafx.fxml.FXML;
//import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;



public class AppController {

    private Cookbook cookbook = new Cookbook();
  
    @FXML
    private VBox recipeList;
    
    public void initialize() {

      CookbookHandler ch = new CookbookHandler();
      cookbook = ch.readFromFile("../cookbook.json");

      recipeList.setMinHeight(cookbook.getRecipes().size()*130);

      for(Recipe recipe : cookbook.getRecipes()) {

        //lager pane til å vise oppskrift

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
        
        // legger til view button
        /*
        Button button = new Button("View");
        button.setLayoutX(pane.getMinWidth() - button.getMinWidth()); // Adjust the x-coordinate as needed
        button.setLayoutY(10); // Adjust the y-coordinate as needed
         */
        
        pane.getChildren().addAll(recipeName, ingredients);
        recipeList.getChildren().add(pane);
        
      }
    }
}
