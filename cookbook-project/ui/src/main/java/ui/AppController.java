package ui;

import java.util.Map.Entry;

import core.Cookbook;
import core.Ingredient;
import core.Recipe;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class AppController {

    private Cookbook cookbook = new Cookbook();
    private Recipe tacoRecipe;
    private Recipe pizzaRecipe;
    private Recipe pastaCarbonaraRecipe;

    public void makeRecipes() {
      Ingredient cheese = new Ingredient("cheese");
      Ingredient ham = new Ingredient("ham");
      Ingredient spaghetti = new Ingredient("spaghetti");
      Ingredient egg = new Ingredient("egg");

      // Additional ingredients for a typical pizza
      Ingredient pizzaDough = new Ingredient("pizza dough");
      Ingredient tomatoSauce = new Ingredient("tomato sauce");
      Ingredient pepperoni = new Ingredient("pepperoni");
      Ingredient mushrooms = new Ingredient("mushrooms");
      Ingredient onions = new Ingredient("onions");

      // Additional ingredients for a typical taco
      Ingredient tacoShell = new Ingredient("taco shell");
      Ingredient groundBeef = new Ingredient("ground beef");
      Ingredient lettuce = new Ingredient("lettuce");
      Ingredient tomatoes = new Ingredient("tomatoes");
      Ingredient salsa = new Ingredient("salsa");

      // Additional ingredients for pasta carbonara
      Ingredient bacon = new Ingredient("bacon");
      Ingredient heavyCream = new Ingredient("heavy cream");
      Ingredient parmesanCheese = new Ingredient("parmesan cheese");

      // New recipes
      this.tacoRecipe = new Recipe("Taco");
      this.pizzaRecipe = new Recipe("Pizza");
      this.pastaCarbonaraRecipe = new Recipe("Pasta Carbonara");

      // Adding pizza ingredients
      pizzaRecipe.addIngredient(cheese, 200.0);
      pizzaRecipe.addIngredient(pizzaDough, 300.0);
      pizzaRecipe.addIngredient(tomatoSauce, 150.0);
      pizzaRecipe.addIngredient(pepperoni, 100.0);
      pizzaRecipe.addIngredient(mushrooms, 50.0);
      pizzaRecipe.addIngredient(onions, 30.0);

      // Adding taco ingredients
      tacoRecipe.addIngredient(cheese, 100.0);
      tacoRecipe.addIngredient(tacoShell, 2.0);
      tacoRecipe.addIngredient(groundBeef, 150.0);
      tacoRecipe.addIngredient(lettuce, 50.0);
      tacoRecipe.addIngredient(tomatoes, 50.0);
      tacoRecipe.addIngredient(salsa, 30.0);

      // Adding pasta carbonara ingredients
      pastaCarbonaraRecipe.addIngredient(cheese, 50.0);
      pastaCarbonaraRecipe.addIngredient(spaghetti, 200.0);
      pastaCarbonaraRecipe.addIngredient(egg, 2.0);
      pastaCarbonaraRecipe.addIngredient(bacon, 100.0);
      pastaCarbonaraRecipe.addIngredient(heavyCream, 150.0);
      pastaCarbonaraRecipe.addIngredient(parmesanCheese, 30.0);

    }
  
    @FXML
    private VBox recipeList;
    
    public void initialize() {
      
      makeRecipes();
      cookbook.addRecipie(pastaCarbonaraRecipe);
      cookbook.addRecipie(pizzaRecipe);
      cookbook.addRecipie(tacoRecipe);

      recipeList.setMinHeight(500);

      for(Recipe recipe : cookbook.getRecipes()) {
        Pane pane = new Pane();
            pane.setMinWidth(330);
            pane.setMaxWidth(330);
            pane.setMinHeight(70);
            pane.setStyle("-fx-padding: 10 10 10 10;");

        Label recipeName = new Label(recipe.getName());
          Font font = Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 16);
          recipeName.setFont(font);
          recipeName.setLayoutX(10);

        Label ingredients = new Label("");
          ingredients.setLayoutX(10);
          for(Entry<Ingredient,Double> ingredient : recipe.getIngredients().entrySet()) {
            String text = ingredients.getText();
            ingredients.setText(text + "\n" + ingredient.getKey().toString() + ":  " + ingredient.getValue());
          }
          ingredients.setText(ingredients.getText() + "\n");

        Button button = new Button("View");
      
        button.setLayoutX(pane.getMinWidth() - button.getMinWidth()); // Adjust the x-coordinate as needed
        button.setLayoutY(10); // Adjust the y-coordinate as needed

        pane.getChildren().addAll(recipeName, ingredients, button);
        recipeList.getChildren().add(pane);
      }
    }
    
}
