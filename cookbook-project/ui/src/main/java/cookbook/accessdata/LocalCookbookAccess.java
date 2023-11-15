package cookbook.accessdata;

import cookbook.core.Cookbook;
import cookbook.core.Recipe;
import cookbook.json.CookbookHandler;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

/**
 * Provides local access to a cookbook stored in a JSON file.
 */
public class LocalCookbookAccess implements CookbookAccess {

  private CookbookHandler ch = new CookbookHandler();
  private String path = "";

  /**
   * Creates a new instance of the class.
   * Sets the path to the cookbook file.
   */
  public LocalCookbookAccess() {
    Path testPath = Paths.get("../persistence/storage/local-cookbook.json");
    boolean setPath = false;
    if (Files.exists(testPath)) {
      this.path = testPath.toString();
      setPath = true;
    }
    if (!setPath) {
      testPath = Paths.get(System.getProperty("user.home") + File.separator
          + "local-cookbook.json");
      if (Files.exists(testPath)) {
        this.path = testPath.toString();
        setPath = true;
      }
    }
    if (!setPath) {
      String writePath = System.getProperty("user.home") + File.separator + "local-cookbook.json";
      try {
        ch.writeToFile(new Cookbook(), writePath);
        this.path = writePath;
      } catch (FileNotFoundException e) {
        throw new RuntimeException("Could not create file");
      }
    }
  }

  /**
   * Fetches the entire cookbook.
   *
   * @return the cookbook, or null if the file is not found.
   */
  @Override
  public Cookbook fetchCookbook() {
    Cookbook cookbook = new Cookbook();
    try {
      cookbook = ch.readFromFile(this.path);
    } catch (FileNotFoundException e) {
      System.out.println("File not found!");
    }
    return cookbook;
  }

  /**
   * Searches for recipes by name.
   *
   * @param recipeName the name or part of the name to search for.
   *
   * @return a cookbook containing the matching recipes.
   */
  @Override
  public Cookbook searchRecipe(String recipeName) {
    Collection<Recipe> searched = fetchCookbook()
        .filterRecipes(recipe -> recipe.getName()
        .toLowerCase().contains(recipeName.toLowerCase()));
    Cookbook tmpCookbook = new Cookbook();
    for (Recipe recipe : searched) {
      tmpCookbook.addRecipe(recipe);
    }
    return tmpCookbook;
  }

  /**
   * Filters recipes by their origin country.
   *
   * @param origin the country of origin to filter by.
   *
   * @return a cookbook containing the matching recipes.
   */
  @Override
  public Cookbook filterByOrigin(String origin) {
    Collection<Recipe> searched = fetchCookbook()
        .filterRecipes(recipe -> recipe.getOriginCountry().equals(origin));
    Cookbook tmpCookbook = new Cookbook();
    for (Recipe recipe : searched) {
      tmpCookbook.addRecipe(recipe);
    }
    return tmpCookbook;
  }

  /**
   * Filters recipes by their type.
   *
   * @param type the type to filter by
   * @return a cookbook containing the matching recipes.
   */
  @Override
  public Cookbook filterByType(String type) {
    Collection<Recipe> searched = fetchCookbook()
        .filterRecipes(recipe -> recipe.getType().equals(type));
    Cookbook tmpCookbook = new Cookbook();
    for (Recipe recipe : searched) {
      tmpCookbook.addRecipe(recipe);
    }
    return tmpCookbook;
  }

  /**
   * Filters recipes that are marked as favorite.
   *
   * @return a cookbook containing the favorite recipes.
   */
  @Override
  public Cookbook filterByFavorite() {
    Collection<Recipe> searched = fetchCookbook()
        .filterRecipes(recipe -> recipe.getFavorite() == true);
    Cookbook tmpCookbook = new Cookbook();
    for (Recipe recipe : searched) {
      tmpCookbook.addRecipe(recipe);
    }
    return tmpCookbook;
  }

  /**
   * Filters recipes based on user preferences.
   * vlg: (vegan, lactose-free, gluten-free)
   *
   * @param vlg a string representing user preferences. 
   * @return a cookbook containing the matching recipes.
   */
  @Override
  public Cookbook filterByPreferences(String vlg) {
    Cookbook tmpCookbook = new Cookbook();
    boolean gluten = vlg.charAt(2) == 'T';
    boolean lactose = vlg.charAt(1) == 'T';
    boolean vegan = vlg.charAt(0) == 'T';
    for (Recipe recipe : fetchCookbook().getRecipes()) {
      if (!((!recipe.getGlutenFree() && gluten) || (!recipe.getLactoseFree() && lactose)
          || (!recipe.getVegan() && vegan))) {
        tmpCookbook.addRecipe(recipe);
      }
    }
    return tmpCookbook;
  }

  /**
   * Updates a recipe in the cookbook.
   *
   * @param recipe the recipe to update.
   */
  @Override
  public void updateRecipe(Recipe recipe) {
    Cookbook tmpCookbook = fetchCookbook();
    tmpCookbook.getRecipes().stream().filter(r -> r.getName()
        .equals(recipe.getName())).forEach(r -> {
          r.setIngredients(recipe.getIngredients());
          r.setDescription(recipe.getDescription());
          r.setOriginCountry(recipe.getOriginCountry());
          r.setType(recipe.getType());
          r.setVegan(recipe.getVegan());
          r.setLactoseFree(recipe.getLactoseFree());
          r.setGlutenFree(recipe.getGlutenFree());
          r.setFavorite(recipe.getFavorite());
        });
    saveCookbook(tmpCookbook);
  }

  /**
   * Removes a recipe from the cookbook.
   *
   * @param recipeName the name of the recipe to remove.
   */
  @Override
  public boolean removeRecipe(String recipeName) {
    Cookbook tmpCookbook = fetchCookbook();
    Collection<Recipe> recipes = tmpCookbook.getRecipes();
    for (Recipe recipe : recipes) {
      if (recipe.getName().equals(recipeName)) {
        tmpCookbook.removeRecipe(recipe);
        saveCookbook(tmpCookbook);
        return true;
      }
    }
    return false;
  }

  /**
   * Adds a new recipe to the cookbook.
   *
   * @param recipe the recipe to add.
   */
  @Override
  public void addRecipe(Recipe recipe) {
    Cookbook tmpCookbook = fetchCookbook();
    if (recipe.getName().strip().isEmpty()) {
      throw new IllegalArgumentException("Recipe name cannot be empty");
    }
    try {
      tmpCookbook.addRecipe(recipe);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Recipe already exists");
    }
    saveCookbook(tmpCookbook);
  }

  /**
   * Saves the cookbook to the file.
   *
   * @param cookbook the cookbook to save.
   */
  private void saveCookbook(Cookbook cookbook) {
    try {
      ch.writeToFile(cookbook, path);
    } catch (FileNotFoundException e) {
      throw new RuntimeException("File not found");
    }
  }

  /**
   * Toggles the favorite status of a recipe.
   *
   * @param recipe the recipe to toggle.
   */
  public void toggleFavorite(Recipe recipe) {
    Cookbook tmpCookbook = fetchCookbook();
    for (Recipe r : tmpCookbook.getRecipes()) {
      if (r.getName().equals(recipe.getName())) {
        r.setFavorite(!recipe.getFavorite());
        saveCookbook(tmpCookbook);
        return;
      }
    }
  }
}