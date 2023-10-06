package cookbook.json;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
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
      
      Recipe pizzaRecipe = new Recipe("Pizza");
      pizzaRecipe.setOriginCountry("Italy");
      pizzaRecipe.addIngredient("cheese", "200.0");
      pizzaRecipe.addIngredient("pizzaDough", "300.0");
      pizzaRecipe.addIngredient("tomatoSauce", "150.0");
      pizzaRecipe.addIngredient("pepperoni", "100.0");
      pizzaRecipe.addIngredient("mushrooms", "50.0");
      pizzaRecipe.addIngredient("onions", "30.0");
      pizzaRecipe.setDescription("Spread pizza dough onto a pan, applying a generous layer of tomato sauce. Sprinkle cheese uniformly and add toppings such as pepperoni, mushrooms, and onions. Bake until the crust turns golden and the cheese bubbles. Allow to cool slightly, slice, and serve.");

      Recipe tacoRecipe = new Recipe("Taco");
      tacoRecipe.setOriginCountry("Mexico");
      tacoRecipe.addIngredient("cheese", "100.0");
      tacoRecipe.addIngredient("tacoShell", "2.0");
      tacoRecipe.addIngredient("groundBeef", "150.0");
      tacoRecipe.addIngredient("lettuce", "50.0");
      tacoRecipe.addIngredient("tomatoes", "55.0");
      tacoRecipe.addIngredient("salsa", "30.0");
      tacoRecipe.setDescription("Begin by browning the beef, adding a blend of cumin, chili powder, garlic powder, oregano, and salt. Once browned, stir in tomato sauce, simmering until the mixture thickens slightly. Warm taco shells, fill with the savory meat, and top with cheese, lettuce, tomatoes, salsa, or your preferred toppings.");
  
      Recipe pastaCarbonaraRecipe = new Recipe("Pasta Carbonara");
      pastaCarbonaraRecipe.setOriginCountry("Italy");
      pastaCarbonaraRecipe.addIngredient("cheese", "50.0");
      pastaCarbonaraRecipe.addIngredient("spaghetti", "215.0");
      pastaCarbonaraRecipe.addIngredient("egg", "2.0");
      pastaCarbonaraRecipe.addIngredient("bacon", "100.0");
      pastaCarbonaraRecipe.addIngredient("heavyCream", "150.0");
      pastaCarbonaraRecipe.addIngredient("parmesanCheese", "30.0");
      pastaCarbonaraRecipe.setDescription("Cook spaghetti until al dente. In a separate pan, cook bacon until crisp, then stir in garlic briefly. Beat eggs, parmesan, and heavy cream together, then combine with the hot, drained pasta, cooking slightly to create a creamy sauce. Add bacon, season, and serve with additional cheese.");
  
      Recipe recipe1 = new Recipe("Spaghetti Bolognese");
      recipe1.setOriginCountry("Italy");
      recipe1.addIngredient("spaghetti", "200.0");
      recipe1.addIngredient("groundBeef", "250.0");
      recipe1.addIngredient("onions", "30.0");
      recipe1.addIngredient("tomatoSauce", "150.0");
      recipe1.addIngredient("garlic", "10.0");
      recipe1.addIngredient("oregano", "5.0");
      recipe1.setDescription("Cook the spaghetti according to package instructions until al dente. In a separate pan, sauté onions and garlic until translucent. Add ground beef and cook until browned. Combine with tomato sauce and simmer. Season with oregano and serve atop the cooked spaghetti.");
  
      Recipe recipe2 = new Recipe("Chicken Stir-Fry");
      recipe2.setOriginCountry("China");
      recipe2.addIngredient("chickenBreast", "300.0");
      recipe2.addIngredient("bellPeppers", "100.0");
      recipe2.addIngredient("broccoli", "150.0");
      recipe2.addIngredient("soySauce", "30.0");
      recipe2.addIngredient("ginger", "10.0");
      recipe2.addIngredient("rice", "200.0");
      recipe2.setDescription("In a wok or large pan, sauté diced chicken breast until fully cooked. Remove from pan and stir-fry bell peppers and broccoli until slightly tender. Add cooked chicken back to the pan, stir in soy sauce and ginger, and continue cooking for a few more minutes. Serve with cooked rice.");
  
      Recipe recipe3 = new Recipe("Caesar Salad");
      recipe3.setOriginCountry("Italy");
      recipe3.addIngredient("lettuce", "100.0");
      recipe3.addIngredient("chickenBreast", "200.0");
      recipe3.addIngredient("croutons", "50.0");
      recipe3.addIngredient("parmesanCheese", "30.0");
      recipe3.addIngredient("caesarDressing", "40.0");
      recipe3.setDescription("Grill or pan-sear chicken breasts until fully cooked and then slice them. In a large bowl, combine lettuce, croutons, and parmesan cheese. Toss with caesar dressing and top with the cooked, sliced chicken breast.");
  
      Recipe recipe4 = new Recipe("Vegetable Curry");
      recipe4.setOriginCountry("India");
      recipe4.addIngredient("potatoes", "200.0");
      recipe4.addIngredient("carrots", "150.0");
      recipe4.addIngredient("peas", "50.0");
      recipe4.addIngredient("coconutMilk", "200.0");
      recipe4.addIngredient("curryPaste", "30.0");
      recipe4.addIngredient("rice", "200.0");
      recipe4.setDescription("Sauté chopped potatoes, carrots, and peas in a large pan. Once slightly tender, add curry paste and continue sautéing for a few minutes. Add coconut milk and simmer until vegetables are cooked through. Serve with cooked rice.");
  
      Recipe recipe5 = new Recipe("Mushroom Risotto");
      recipe5.setOriginCountry("Italy");
      recipe5.addIngredient("arborioRice", "200.02");
      recipe5.addIngredient("mushrooms", "150.0");
      recipe5.addIngredient("onions", "30.0");
      recipe5.addIngredient("vegetableBroth", "250.0");
      recipe5.addIngredient("whiteWine", "100.0");
      recipe5.setDescription("Sauté onions and mushrooms until browned. Add arborio rice and continue to sauté for a few minutes. Gradually add vegetable broth, continuously stirring, until rice is fully cooked. Near the end of cooking, stir in white wine and cook until evaporated.");
  
      Recipe recipe6 = new Recipe("Spinach and Feta Stuffed Chicken");
      recipe6.setOriginCountry("Greece");
      recipe6.addIngredient("chickenBreast", "300.0");
      recipe6.addIngredient("spinach", "100.0");
      recipe6.addIngredient("fetaCheese", "50.0");
      recipe6.addIngredient("garlic", "10.0");
      recipe6.addIngredient("lemonJuice", "20.0");
      recipe6.setDescription("Preheat the oven to 375°F (190°C). In a pan, sauté spinach and garlic until wilted, then mix with crumbled feta cheese. Slice a pocket into each chicken breast and stuff it with the spinach and feta mixture. Seal with toothpicks and bake until the chicken is cooked through. Drizzle with lemon juice before serving.");
  
      Recipe recipe7 = new Recipe("Baked Salmon");
      recipe7.setOriginCountry("Norway");
      recipe7.addIngredient("salmonFilet", "200.0");
      recipe7.addIngredient("lemon", "30.0");
      recipe7.addIngredient("dill", "5.0");
      recipe7.addIngredient("oliveOil", "20.0");
      recipe7.setDescription("Preheat the oven to 375°F (190°C). Place the salmon filet on a baking sheet and drizzle with olive oil and freshly squeezed lemon juice, then sprinkle with dill. Bake until the salmon is cooked through and flakes easily with a fork.");
  
      Recipe recipe8 = new Recipe("Veggie Wrap");
      recipe8.setOriginCountry("America");
      recipe8.addIngredient("tortilla", "1.0");
      recipe8.addIngredient("hummus", "50.0");
      recipe8.addIngredient("cucumber", "30.0");
      recipe8.addIngredient("bellPeppers", "40.0");
      recipe8.addIngredient("lettuce", "20.0");
      recipe8.setDescription("Lay out a tortilla and spread a generous layer of hummus over the entire surface. Add slices of cucumber and bell peppers, then top with lettuce. Roll the tortilla tightly, slice in half, and serve.");
  
      Recipe recipe9 = new Recipe("Fruit Salad");
      recipe9.setOriginCountry("Brazil");
      recipe9.addIngredient("strawberries", "100.0");
      recipe9.addIngredient("kiwi", "50.0");
      recipe9.addIngredient("grapes", "50.0");
      recipe9.addIngredient("honey", "20.0");
      recipe9.setDescription("In a large bowl, combine chopped strawberries, kiwi, and grapes. Drizzle with honey and gently toss until all the fruit is coated. Serve immediately, or refrigerate for later use.");
  
      Recipe recipe10 = new Recipe("Oatmeal");
      recipe10.setOriginCountry("America");
      recipe10.addIngredient("rolledOats", "100.0");
      recipe10.addIngredient("milk", "200.0");
      recipe10.addIngredient("honey", "20.0");
      recipe10.addIngredient("banana", "1.0");
      recipe10.addIngredient("cinnamon", "5.0");
      recipe10.setDescription("In a pot, combine rolled oats and milk. Cook over medium heat, stirring frequently, until the oats are soft and have absorbed most of the milk. Add sliced banana and honey, stirring until combined. Sprinkle with cinnamon before serving.");
  
      
      
      //add recipies to cookbook
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
