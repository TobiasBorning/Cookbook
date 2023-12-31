package cookbook.springboot;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cookbook.core.Cookbook;
import cookbook.core.Recipe;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for managing cookbook operations.
 */
@Service
public class CookbookService {

  private Gson gson;
  private static final String COOKBOOK_PATH = "../persistence/storage/remote-cookbook.json";

  /**
   * Constructs a new CookbookService.
   */
  public CookbookService() {
    this.gson = new GsonBuilder().setPrettyPrinting().create();
    readCookbook();
  }

  /**
   * Reads the cookbook from the file.
   *
   * @return the cookbook.
   * @throws RuntimeException if the cookbook file cannot be found.
   */
  public Cookbook readCookbook() {
    try (FileReader reader = new FileReader(COOKBOOK_PATH)) {
      return gson.fromJson(reader, Cookbook.class);
    } catch (IOException e) {
      throw new RuntimeException("Could not find cookbook file.", e);
    }
  }

  /**
   * Writes the cookbook to the file.
   *
   * @param cookbook the cookbook to write.
   * @throws RuntimeException if the cookbook file cannot be written to.
   */
  public void writeCookbook(final Cookbook cookbook) {
    try (FileWriter writer = new FileWriter(COOKBOOK_PATH)) {
      gson.toJson(cookbook, writer);
    } catch (IOException e) {
      throw new RuntimeException("Could not write cookbook file.", e);
    }
  }

  /**
   * Updates the cookbook and writes it to the file.
   *
   * @param cookbook the updated cookbook.
   */
  public void updateCookbook(final Cookbook cookbook) {
    //can add validation here
    writeCookbook(cookbook);
  }

  /**
   * Adds a recipe to the cookbook.
   *
   * @param recipeJson the JSON representation of the recipe.
   * @param cookbook the cookbook to add the recipe to.
   * @return true if the recipe was added, false otherwise.
   */
  public boolean addRecipe(final String recipeJson, final Cookbook cookbook) {
    Recipe recipe = gson.fromJson(recipeJson, Recipe.class);
    try {
      cookbook.addRecipe(recipe);
      updateCookbook(cookbook);
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    } 
  }

  /**
   * Removes a recipe from the cookbook.
   *
   * @param recipeName the name of the recipe to remove.
   * @param cookbook the cookbook to remove the recipe from.
   * @return a response entity with status OK if the recipe was removed, NOT_FOUND otherwise.
   */
  public ResponseEntity<Void> deleteRecipe(final String recipeName, final Cookbook cookbook) {
    for (Recipe recipe : cookbook.getRecipes()) {
      if (recipe.getName().equals(recipeName)) {
        cookbook.removeRecipe(recipe);
        updateCookbook(cookbook);
        return new ResponseEntity<Void>(HttpStatus.OK);
      }
    }
    return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
  }

  /**
   * Retrieves recipes by name.
   *
   * @param name the name or part of the name to search for.
   * @param cookbook the cookbook to search in.
   * @return a cookbook containing the matching recipes.
   */
  public Cookbook getRecipe(final String name, final Cookbook cookbook) {
    Cookbook tmpCookbook = new Cookbook();
    for (Recipe recipe : cookbook.getRecipes()) {
      if (recipe.getName().toLowerCase().contains(name.toLowerCase())) {
        tmpCookbook.addRecipe(recipe);
      }
    }
    return tmpCookbook;
  }

  /**
   * Filters recipes by their origin country.
   *
   * @param origin the country of origin to filter by.
   * @param cookbook the cookbook to filter.
   * @return a cookbook containing the matching recipes.
   */
  public Cookbook filterByOrigin(final String origin, final Cookbook cookbook) {
    Cookbook tmpCookbook = new Cookbook();
    for (Recipe recipe : cookbook.getRecipes()) {
      if (recipe.getOriginCountry().equals(origin)) {
        tmpCookbook.addRecipe(recipe);
      }
    }
    return tmpCookbook;
  }

  /**
   * Filters recipes by their type.
   *
   * @param type the type to filter by.
   * @param cookbook the cookbook to filter.
   * @return a cookbook containing the matching recipes.
   */
  public Cookbook filterByType(final String type, final Cookbook cookbook) {
    Cookbook tmpCookbook = new Cookbook();
    for (Recipe recipe : cookbook.getRecipes()) {
      if (recipe.getType().toLowerCase().equals(type.toLowerCase())) {
        tmpCookbook.addRecipe(recipe);
      }
    }
    return tmpCookbook;
  }

  /**
   * Filters recipes that are marked as favorite.
   *
   * @param cookbook the cookbook to filter.
   * @return a cookbook containing the favorite recipes.
   */
  public Cookbook filterByFavorite(final Cookbook cookbook) {
    Cookbook tmpCookbook = new Cookbook();
    for (Recipe recipe : cookbook.getRecipes()) {
      if (recipe.getFavorite()) {
        tmpCookbook.addRecipe(recipe);
      }
    }
    return tmpCookbook;
  }

  /**
   * Filters recipes based on user preferences.
   *
   * @param vlg a string representing user preferences.
   * @param cookbook the cookbook to filter.
   * @return a cookbook containing the matching recipes.
   */
  public Cookbook filterByPreferences(final String vlg, final Cookbook cookbook) {
    Cookbook tmpCookbook = new Cookbook();
    
    boolean gluten = vlg.charAt(2) == 'T';
    boolean lactose = vlg.charAt(1) == 'T';
    boolean vegan = vlg.charAt(0) == 'T';

    for (Recipe recipe : cookbook.getRecipes()) {
      if (!((!recipe.getGlutenFree() && gluten) || (!recipe.getLactoseFree() && lactose) 
          || (!recipe.getVegan() && vegan))) {
        tmpCookbook.addRecipe(recipe);
      }
    }
    return tmpCookbook;
  }

  /**
   * Sets a recipe as favorite.
   *
   * @param recipeName the name of the recipe to set as favorite.
   * @param cookbook the cookbook to update.
   * @return the updated Recipe, null if it was not found.
   */
  public Recipe toggleFavorite(final String recipeName, final Cookbook cookbook) {
    Recipe tmpRecipe = null;
    for (Recipe recipe : cookbook.getRecipes()) {
      if (recipe.getName().equals(recipeName)) {
        recipe.setFavorite(!recipe.getFavorite());
        tmpRecipe = recipe;
      }
    }
    updateCookbook(cookbook);
    return tmpRecipe;
  }
    
  /**
   * Updates a recipe.
   *
   * @param recipeName the name of the recipe to update.
   * @param recipeJson the updated recipe.
   * @param cookbook the cookbook to update
   * @return the updated Recipe, null if it was not found.
   */
  public Recipe updateRecipe(final String recipeName, 
      final String recipeJson, final Cookbook cookbook) {
    Recipe tmpRecipe = null;
    String recipeNameFormated = recipeName.replace("%20", " ");
    for (Recipe recipe : cookbook.getRecipes()) {
      if (recipe.getName().equals(recipeNameFormated)) {
        tmpRecipe = gson.fromJson(recipeJson, Recipe.class);
        recipe.setName(tmpRecipe.getName());
        recipe.setOriginCountry(tmpRecipe.getOriginCountry());
        recipe.setType(tmpRecipe.getType());
        recipe.setIngredients(tmpRecipe.getIngredients());
        recipe.setGlutenFree(tmpRecipe.getGlutenFree());
        recipe.setLactoseFree(tmpRecipe.getLactoseFree());
        recipe.setVegan(tmpRecipe.getVegan());
        recipe.setFavorite(tmpRecipe.getFavorite());
      }
    }
    updateCookbook(cookbook);
    return tmpRecipe;
  }

  
}
