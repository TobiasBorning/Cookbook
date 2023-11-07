package cookbook.accessdata;

import cookbook.core.Cookbook;
import cookbook.core.Recipe;

public interface CookbookAccess {

  /**
   * Fetches the entire cookbook.
   * 
   * @return the cookbook, or null if the file is not found.
   */
  public Cookbook fetchCookbook();

  /**
   * Searches for recipes by name.
   * 
   * @param recipeName the name or part of the name to search for.
   * @return a cookbook containing the matching recipes.
   */
  public Cookbook searchRecipe(String recipeName);

  /**
   * Filters recipes by their origin country.
   * 
   * @param origin the country of origin to filter by.
   * @return a cookbook containing the matching recipes.
   */
  public Cookbook filterByOrigin(String origin);

  /**
   * Filters recipes by their type.
   * 
   * @param type the type to filter by.
   * @return a cookbook containing the matching recipes.
   */
  public Cookbook filterByType(String type);

  /**
   * Filters recipes that are marked as favorite.
   * 
   * @return a cookbook containing the favorite recipes.
   */
  public Cookbook filterByFavorite();

  /**
   * Filters recipes based on user preferences.
   * 
   * @param vlg a string representing user preferences (vegan, lactose-free, gluten-free)
   * @return a cookbook containing the matching recipes.
   */
  public Cookbook filterByPreferences(String vlg);

  /**
   * Updates a recipe in the cookbook.
   * 
   * @param recipe the recipe to update.
   */
  public void updateRecipe(Recipe recipe);

  /**
   * Removes a recipe from the cookbook.
   * 
   * @param recipeName the name of the recipe to remove.
   */
  public boolean removeRecipe(String recipeName);

  /**
   * Adds a new recipe to the cookbook.
   * 
   * @param recipe the recipe to add.
   */
  public void addRecipe(Recipe recipe);

  /**
   * Marks a recipe as favorite.
   * 
   * @param recipe the recipe to mark.
   */
  public void toggleFavorite(Recipe recipe);
}
