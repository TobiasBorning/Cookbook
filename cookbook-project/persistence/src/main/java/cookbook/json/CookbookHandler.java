package cookbook.json;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

import com.google.gson.Gson;

import cookbook.core.Cookbook;
import cookbook.core.Recipe;

public class CookbookHandler {

    /**
       * Writes cookbook to file using Gson.
       *
       * @param cookbook the cookbook to be written
       * @param path     path of json file
       *    
       * @throws FileNotFoundException throws exception if the path is not found
       */
    public void writeToFile(Cookbook cookbook, String path) throws FileNotFoundException {
      // making gson object
      Gson gson = new Gson();
      // converting 
      String json = gson.toJson(cookbook);
      String filePath = path;

      try (FileWriter writer = new FileWriter(filePath)) {
          writer.write(json);
      } catch (IOException e) {
          e.printStackTrace();
      }
    }

    /**
     * Reads cookbook from file via filepath using Gson.
     *
     * @param path  
     * @return cookbook from json file
     * @throws FileNotFoundException throws exception
     */
    public Cookbook readFromFile(String path) throws FileNotFoundException{
      String filePath = path;

      try (Reader reader = new FileReader(filePath)) {
          // Create a Gson instance
          Gson gson = new Gson();

          // Use Gson to parse the JSON data into an instance of your Java class
          Cookbook cookbook = gson.fromJson(reader, Cookbook.class);

          return cookbook;

      } catch (IOException e) {
          e.printStackTrace();
          throw new FileNotFoundException("File not found");
      }
    }
    
    public static void makeRecipes() {

      Cookbook cookbook = new Cookbook();
      // New recipes
      Recipe tacoRecipe = new Recipe("Taco");
      Recipe pizzaRecipe = new Recipe("Pizza");
      Recipe pastaCarbonaraRecipe = new Recipe("Pasta Carbonara");
  
      tacoRecipe.setOriginCountry("Mexico");
      pizzaRecipe.setOriginCountry("Italy");
      pastaCarbonaraRecipe.setOriginCountry("Italy");

      tacoRecipe.setDescription("Add the beef to a large skillet over medium-high heat. Break the meat apart with a wooden spoon. Add the chili powder, cumin, salt, oregano, garlic powder, and pepper to the meat. Stir well. Cook until the meat is cooked through, about 6-8 minutes, stirring occasionally.\n" + //
          "Reduce the heat to medium. Add the tomato sauce and water. Stir to combine. Cook, stirring occasionally, for 7-8 minutes, until some of the liquid evaporates but the meat mixture is still a little saucy. Remove from the heat.\n" + //
          "Warm the taco shells according to their package directions.\n" + //
          "Fill the taco shells with 2 heaping tablespoons of taco meat. Top with desired taco toppings: shredded cheese, shredded lettuce, chopped tomatoes, diced red onion, taco sauce, sour cream, guacamole, etc.");
      
      // Adding pizza ingredients
      pizzaRecipe.addIngredient("cheese", "200.0");
      pizzaRecipe.addIngredient("pizzaDough", "300.0");
      pizzaRecipe.addIngredient("tomatoSauce", "150.0");
      pizzaRecipe.addIngredient("pepperoni", "100.0");
      pizzaRecipe.addIngredient("mushrooms", "50.0");
      pizzaRecipe.addIngredient("onions", "30.0");
  
      // Adding taco ingredients
      tacoRecipe.addIngredient("cheese", "100.0");
      tacoRecipe.addIngredient("tacoShell", "2.0");
      tacoRecipe.addIngredient("groundBeef", "150.0");
      tacoRecipe.addIngredient("lettuce", "50.0");
      tacoRecipe.addIngredient("tomatoes", "55.0");
      tacoRecipe.addIngredient("salsa", "30.0");
  
      // Adding pasta carbonara ingredients
      pastaCarbonaraRecipe.addIngredient("cheese", "50.0");
      pastaCarbonaraRecipe.addIngredient("spaghetti", "215.0");
      pastaCarbonaraRecipe.addIngredient("egg", "2.0");
      pastaCarbonaraRecipe.addIngredient("bacon", "100.0");
      pastaCarbonaraRecipe.addIngredient("heavyCream", "150.0");
      pastaCarbonaraRecipe.addIngredient("parmesanCheese", "30.0");
      
      // generert med chat:
  
      Recipe recipe1 = new Recipe("Spaghetti Bolognese");
      recipe1.setOriginCountry("Italy");
      recipe1.addIngredient("spaghetti", "200.0");
      recipe1.addIngredient("groundBeef", "250.0");
      recipe1.addIngredient("onions", "30.0");
      recipe1.addIngredient("tomatoSauce", "150.0");
      recipe1.addIngredient("garlic", "10.0");
      recipe1.addIngredient("oregano", "5.0");
  
      Recipe recipe2 = new Recipe("Chicken Stir-Fry");
      recipe2.setOriginCountry("China");
      recipe2.addIngredient("chickenBreast", "300.0");
      recipe2.addIngredient("bellPeppers", "100.0");
      recipe2.addIngredient("broccoli", "150.0");
      recipe2.addIngredient("soySauce", "30.0");
      recipe2.addIngredient("ginger", "10.0");
      recipe2.addIngredient("rice", "200.0");
  
      Recipe recipe3 = new Recipe("Caesar Salad");
      recipe3.setOriginCountry("Italy");
      recipe3.addIngredient("lettuce", "100.0");
      recipe3.addIngredient("chickenBreast", "200.0");
      recipe3.addIngredient("croutons", "50.0");
      recipe3.addIngredient("parmesanCheese", "30.0");
      recipe3.addIngredient("caesarDressing", "40.0");
  
      Recipe recipe4 = new Recipe("Vegetable Curry");
      recipe4.setOriginCountry("India");
      recipe4.addIngredient("potatoes", "200.0");
      recipe4.addIngredient("carrots", "150.0");
      recipe4.addIngredient("peas", "50.0");
      recipe4.addIngredient("coconutMilk", "200.0");
      recipe4.addIngredient("curryPaste", "30.0");
      recipe4.addIngredient("rice", "200.0");
  
      Recipe recipe5 = new Recipe("Mushroom Risotto");
      recipe5.setOriginCountry("Italy");
      recipe5.addIngredient("arborioRice", "200.02");
      recipe5.addIngredient("mushrooms", "150.0");
      recipe5.addIngredient("onions", "30.0");
      recipe5.addIngredient("vegetableBroth", "250.0");
      recipe5.addIngredient("whiteWine", "100.0");
  
      Recipe recipe6 = new Recipe("Spinach and Feta Stuffed Chicken");
      recipe6.setOriginCountry("Greece");
      recipe6.addIngredient("chickenBreast", "300.0");
      recipe6.addIngredient("spinach", "100.0");
      recipe6.addIngredient("fetaCheese", "50.0");
      recipe6.addIngredient("garlic", "10.0");
      recipe6.addIngredient("lemonJuice", "20.0");
  
      Recipe recipe7 = new Recipe("Baked Salmon");
      recipe7.setOriginCountry("Norway");
      recipe7.addIngredient("salmonFilet", "200.0");
      recipe7.addIngredient("lemon", "30.0");
      recipe7.addIngredient("dill", "5.0");
      recipe7.addIngredient("oliveOil", "20.0");
  
      Recipe recipe8 = new Recipe("Veggie Wrap");
      recipe8.setOriginCountry("America");
      recipe8.addIngredient("tortilla", "1.0");
      recipe8.addIngredient("hummus", "50.0");
      recipe8.addIngredient("cucumber", "30.0");
      recipe8.addIngredient("bellPeppers", "40.0");
      recipe8.addIngredient("lettuce", "20.0");
  
      Recipe recipe9 = new Recipe("Fruit Salad");
      recipe9.setOriginCountry("Brazil");
      recipe9.addIngredient("strawberries", "100.0");
      recipe9.addIngredient("kiwi", "50.0");
      recipe9.addIngredient("grapes", "50.0");
      recipe9.addIngredient("honey", "20.0");
  
      Recipe recipe10 = new Recipe("Oatmeal");
      recipe10.setOriginCountry("America");
      recipe10.addIngredient("rolledOats", "100.0");
      recipe10.addIngredient("milk", "200.0");
      recipe10.addIngredient("honey", "20.0");
      recipe10.addIngredient("banana", "1.0");
      recipe10.addIngredient("cinnamon", "5.0");
  
      
      
      //add recipies
      cookbook.addRecipe(tacoRecipe);
      cookbook.addRecipe(pizzaRecipe);
      cookbook.addRecipe(pastaCarbonaraRecipe);
      cookbook.addRecipe(recipe1);
      cookbook.addRecipe(recipe2);
      cookbook.addRecipe(recipe3);
      cookbook.addRecipe(recipe4);
      cookbook.addRecipe(recipe5);
      cookbook.addRecipe(recipe6);
      cookbook.addRecipe(recipe7);
      cookbook.addRecipe(recipe8);
      cookbook.addRecipe(recipe9);
      cookbook.addRecipe(recipe10);
  
      CookbookHandler ch = new CookbookHandler();
      try {
        ch.writeToFile(cookbook,"cookbook-project/persistence/cookbook.json");
      } catch (FileNotFoundException e) {
        System.out.println("File not found");
      }
    }
  
    public static void main(String[] args) {
      makeRecipes();
    }
     
}
