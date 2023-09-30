package cookbook.ui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddRecipeController {

  @FXML
  private VBox ingredientsContainer;

  public void addIngredient(ActionEvent e) {

    System.out.println("hei");
    Pane pane = new Pane();
        pane.setMinWidth(330);
        pane.setMaxWidth(400);
        pane.setMinHeight(40);
        pane.setStyle("-fx-padding: 10 10 10 10; -fx-border-width: 0px 0px 3px 0px; -fx-border-color: #000000;");

    

    TextField amount = new TextField();
    amount.setMaxWidth(40);
    amount.setLayoutX(100); // Adjust the x-coordinate as needed
    amount.setLayoutY(10); // Adjust the y-coordinate as needed

    TextField ingredientName = new TextField();
    amount.setMaxWidth(40);
    ingredientName.setLayoutX(10); // Adjust the x-coordinate as needed
    ingredientName.setLayoutY(10); // Adjust the y-coordinate as needed

    pane.getChildren().addAll(ingredientName, amount);
    ingredientsContainer.getChildren().add(pane);
  }

  //Switches from AddRecipe scene to main scene
  public void switchToMainScene(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("CookbookApp.fxml"));
    Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }
  
}
