package cookbook.core;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import cookbook.json.CookbookHandler;

public class Cookbook {

  private Collection<Recipe> recipes = new ArrayList<>();


  //adding and removing recipies

/**
   * Adds recipe to Cookbook.
   *
   * @param recipe is added to the Collection of recipes
   *     
   * @throws IllegalArgumentException if recipe is null, or if recipe already
   *    is in the cookbook.
	 */
  public void addRecipe(Recipe recipe) {
    if (recipe == null) {
      throw new IllegalArgumentException("Invalid recipe");
    }
    if (recipes.contains(recipe)){
      throw new IllegalArgumentException("Recipe already in cook book");
    }
    recipes.add(recipe);
  }

  /**
   * Removes recipe from Cookbook.
   *
   * @param recipe is removed from the Collection of recipes
   *     
   * @throws IllegalArgumentException if the cook book does not contain the recipe
	 */
  public void removeRecipie(Recipe recipie) {
    if (!recipes.contains(recipie)) {
      throw new IllegalArgumentException("Recipe not in cookbook");
    }
    recipes.remove(recipie);
  }

  //filter function
/**
   * Filters the wanted recipes.
   *
   * @param pred is a predicate to filter out given recipes, 
   *      Can filter on origin countries of different dishes, or on certain grocery items
	 */
  public Collection<Recipe> filterRecipies(Predicate<Recipe> pred) {
    return recipes.stream().filter(pred).collect(Collectors.toList());
  }
/**
   * Retrieves certain recipes
   *
   * @return a Collection of Recipe objects
	 */
  public Collection<Recipe> getRecipes() {
    return new ArrayList<>(recipes);
  }
/**
   * Generates recipes and writes them to file
	 */
  public static void makeRecipes() {

    Cookbook cookbook = new Cookbook();
    // New recipes
    Recipe tacoRecipe = new Recipe("Taco");
    Recipe pizzaRecipe = new Recipe("Pizza");
    Recipe pastaCarbonaraRecipe = new Recipe("Pasta Carbonara");

    // Adding pizza ingredients
    pizzaRecipe.addIngredient("cheese", 200.0);
    pizzaRecipe.addIngredient("pizzaDough", 300.0);
    pizzaRecipe.addIngredient("tomatoSauce", 150.0);
    pizzaRecipe.addIngredient("pepperoni", 100.0);
    pizzaRecipe.addIngredient("mushrooms", 50.0);
    pizzaRecipe.addIngredient("onions", 30.0);

    // Adding taco ingredients
    tacoRecipe.addIngredient("cheese", 100.0);
    tacoRecipe.addIngredient("tacoShell", 2.0);
    tacoRecipe.addIngredient("groundBeef", 150.0);
    tacoRecipe.addIngredient("lettuce", 50.0);
    tacoRecipe.addIngredient("tomatoes", 50.0);
    tacoRecipe.addIngredient("salsa", 30.0);

    // Adding pasta carbonara ingredients
    pastaCarbonaraRecipe.addIngredient("cheese", 50.0);
    pastaCarbonaraRecipe.addIngredient("spaghetti", 215.0);
    pastaCarbonaraRecipe.addIngredient("egg", 2.0);
    pastaCarbonaraRecipe.addIngredient("bacon", 100.0);
    pastaCarbonaraRecipe.addIngredient("heavyCream", 150.0);
    pastaCarbonaraRecipe.addIngredient("parmesanCheese", 30.0);
    
    // generert med chat:

    Recipe recipe1 = new Recipe("Spaghetti Bolognese");
    recipe1.addIngredient("spaghetti", 200.0);
    recipe1.addIngredient("groundBeef", 250.0);
    recipe1.addIngredient("onions", 30.0);
    recipe1.addIngredient("tomatoSauce", 150.0);
    recipe1.addIngredient("garlic", 10.0);
    recipe1.addIngredient("oregano", 5.0);

    Recipe recipe2 = new Recipe("Chicken Stir-Fry");
    recipe2.addIngredient("chickenBreast", 300.0);
    recipe2.addIngredient("bellPeppers", 100.0);
    recipe2.addIngredient("broccoli", 150.0);
    recipe2.addIngredient("soySauce", 30.0);
    recipe2.addIngredient("ginger", 10.0);
    recipe2.addIngredient("rice", 200.0);

    Recipe recipe3 = new Recipe("Caesar Salad");
    recipe3.addIngredient("lettuce", 100.0);
    recipe3.addIngredient("chickenBreast", 200.0);
    recipe3.addIngredient("croutons", 50.0);
    recipe3.addIngredient("parmesanCheese", 30.0);
    recipe3.addIngredient("caesarDressing", 40.0);

    Recipe recipe4 = new Recipe("Vegetable Curry");
    recipe4.addIngredient("potatoes", 200.0);
    recipe4.addIngredient("carrots", 150.0);
    recipe4.addIngredient("peas", 50.0);
    recipe4.addIngredient("coconutMilk", 200.0);
    recipe4.addIngredient("curryPaste", 30.0);
    recipe4.addIngredient("rice", 200.0);

    Recipe recipe5 = new Recipe("Mushroom Risotto");
    recipe5.addIngredient("arborioRice", 200.0);
    recipe5.addIngredient("mushrooms", 150.0);
    recipe5.addIngredient("onions", 30.0);
    recipe5.addIngredient("vegetableBroth", 250.0);
    recipe5.addIngredient("whiteWine", 100.0);

    Recipe recipe6 = new Recipe("Spinach and Feta Stuffed Chicken");
    recipe6.addIngredient("chickenBreast", 300.0);
    recipe6.addIngredient("spinach", 100.0);
    recipe6.addIngredient("fetaCheese", 50.0);
    recipe6.addIngredient("garlic", 10.0);
    recipe6.addIngredient("lemonJuice", 20.0);

    Recipe recipe7 = new Recipe("Baked Salmon");
    recipe7.addIngredient("salmonFilet", 200.0);
    recipe7.addIngredient("lemon", 30.0);
    recipe7.addIngredient("dill", 5.0);
    recipe7.addIngredient("oliveOil", 20.0);

    Recipe recipe8 = new Recipe("Veggie Wrap");
    recipe8.addIngredient("tortilla", 1.0);
    recipe8.addIngredient("hummus", 50.0);
    recipe8.addIngredient("cucumber", 30.0);
    recipe8.addIngredient("bellPeppers", 40.0);
    recipe8.addIngredient("lettuce", 20.0);

    Recipe recipe9 = new Recipe("Fruit Salad");
    recipe9.addIngredient("strawberries", 100.0);
    recipe9.addIngredient("kiwi", 50.0);
    recipe9.addIngredient("grapes", 50.0);
    recipe9.addIngredient("honey", 20.0);

    Recipe recipe10 = new Recipe("Oatmeal");
    recipe10.addIngredient("rolledOats", 100.0);
    recipe10.addIngredient("milk", 200.0);
    recipe10.addIngredient("honey", 20.0);
    recipe10.addIngredient("banana", 1.0);
    recipe10.addIngredient("cinnamon", 5.0);

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
      ch.writeToFile(cookbook,"cookbook-project/cookbook.json");
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    }
  }

  public static void main(String[] args) {
    makeRecipes();
  }

}
