package cookbook.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Cookbook class for creating a Cookbook object.
 */
public class Cookbook {

  private Collection<Recipe> recipes = new ArrayList<>();

  /**
   * Adds recipe to Cookbook.
   *
   * @param recipe is added to the Collection of recipes
   *     
   * @throws IllegalArgumentException if recipe is null, or if recipe already is in the cookbook.
   */
  public void addRecipe(Recipe recipe) {
    if (recipe == null) {
      throw new IllegalArgumentException("Invalid recipe");
    }
    else if (recipes.stream().map(r -> r.getName().strip().toLowerCase())
        .collect(Collectors.toList())
        .contains(recipe.getName().strip().toLowerCase())) {
      System.out.println("Recipe already in cookbook");
      throw new IllegalArgumentException("Recipe already in cookbook");
    }
    recipes.add(recipe);
  }

  /**
   * Adds recipes to Cookbook.
   *
   * @param recipes Collection of recipes
   *     
   * @throws IllegalArgumentException if recipes is empty
   */
  public void addRecipes(Collection<Recipe> recipes) {
    if (recipes.isEmpty()) {
      throw new IllegalArgumentException("Invalid recipe");
    } else {
      this.recipes = recipes;
    } 
  }

  /**
   * Removes recipe from Cookbook.
   *
   * @param recipe is removed from the Collection of recipes
   *     
   * @throws IllegalArgumentException if the cook book does not contain the recipe
   */
  public void removeRecipe(Recipe recipe) {
    if (!recipes.contains(recipe)) {
      throw new IllegalArgumentException("Recipe not in cookbook.");
    }
    recipes.remove(recipe);
  }
  
  /**
   * Filters the wanted recipes.
   *
   * @param pred is a predicate to filter out given recipes,
   *             can filter on origin countries of different dishes, or on certain grocery items
   * @return a Collection of Recipe objects
   */
  public Collection<Recipe> filterRecipes(Predicate<Recipe> pred) {
    return recipes.stream().filter(pred).collect(Collectors.toList());
  }
  
  /**
   * Retrieves certain recipes.
   *
   * @return a Collection of Recipe objects
   */
  public Collection<Recipe> getRecipes() {
    return new ArrayList<>(recipes);
  }
}
