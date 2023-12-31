package cookbook.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Recipe class for creating a recipe object.
 */
public class Recipe {

  private String name;
  private Map<String, String> ingredients = new HashMap<>();
  private String originCountry;
  private String description;

  // Default value for the following variables is false
  private boolean favorite;
  private boolean vegan;
  private boolean glutenFree;
  private boolean lactoseFree;
  private String type;

  /**
   * Constructor for creating a Recipe object with name, ingredients, origincountry and description.
   *
   * @param name The name of the Recipe
   * @param ingredients The ingredients and their amounts as a map.
   * @param originCountry This indicates the country from which the ingredient originates.
   * @param type The type of the recipe.
   * @param description The cooking-methode, how to make the recipe. 
   * @param favorite The recipe is a favorite or not.
   * @param isVegan The recipe is vegan or not.
   * @param isGlutenFree The recipe is glutenfree or not.
   * @param isLactoseFree The recipe is lactosefree or not.
   * 
   */ 
  public Recipe(String name, Map<String, String> ingredients, String originCountry,
                String type, String description, boolean favorite, boolean isVegan,
                boolean isGlutenFree, boolean isLactoseFree) {
    this.name = name;
    this.ingredients = ingredients;
    this.originCountry = originCountry;
    this.description = description;
    this.favorite = favorite;
    this.vegan = isVegan;
    this.glutenFree = isGlutenFree;
    this.lactoseFree = isLactoseFree;
    setType(type);
  }

  /**
   * Empty constructor for creating a Recipe object.
   */
  public Recipe() {
    this.type = "Unknown";
  }

  /**
   * Set the name of the Recipe.
   *
   * @param name The new name for the Recipe.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Set the ingredients of the Recipe.
   *
   * @param ingredients The ingredients and their amounts as a map.
   */
  public void setIngredients(Map<String, String> ingredients) {
    this.ingredients = ingredients;
  }

  /**
   * Get the origin country of the Recipe.
   *
   * @return The origin country of the Recipe.
   */
  public String getOriginCountry() {
    return originCountry;
  }

  /**
   * Get the description of the Recipe.
   *
   * @return The description of the Recipe.
   */
  public String getDescription() {
    return description;
  }

  /**
   * Get the name of the Recipe.
   *
   * @return The name of the Recipe.
   */
  public String getName() {
    return this.name;
  }

  /**
   * Get a copy of the ingredients and their amounts as a map.
   *
   * @return A copy of the ingredients map.
   */
  public Map<String, String> getIngredients() {
    return ingredients;
  }

  /**
   * Set the origin country of the Recipe.
   *
   * @param originCountry The new origin country for the Recipe.
   */
  public void setOriginCountry(String originCountry) {
    this.originCountry = originCountry;
  }

  /**
    * Set the type of the Recipe.
    *
    * @param type The new origin country for the Recipe.
    */
  public void setType(String type) {
    Collection<String> types = new ArrayList<>(
        Arrays.asList("Breakfast", "Lunch", "Dinner", "Dessert"));
    if (types.contains(type)) {
      this.type = type;
    } else {
      this.type = "Unknown";
    }
  }
  
  /**
   * Get the type of the Recipe.
   *
   * @return The type of the Recipe.
   */
  public String getType() {
    return type;
  }

  /**
   * Set the description of the Recipe.
   *
   * @param description The new description for the Recipe.
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Add an ingredient to the Recipe.
   *
   * @param ingredient The name of the ingredient.
   * @param amount     The amount of the ingredient.
   * @throws IllegalArgumentException If the ingredient is already in the map.
   */
  public void addIngredient(String ingredient, String amount) {
    if (ingredients.keySet().contains(ingredient)) {
      throw new IllegalArgumentException("Ingredient already in map");
    }
    ingredients.put(ingredient, amount);
  }

  /**
   * Remove an ingredient from the Recipe.
   *
   * @param ingredient The name of the ingredient to remove.
   * @throws IllegalArgumentException If the ingredient is not in the map.
   */
  public void removeIngredient(String ingredient) {
    if (!ingredients.keySet().contains(ingredient)) {
      throw new IllegalArgumentException("Ingredient not in map");
    }
    ingredients.remove(ingredient);
  }

  /**
   * Checks if the current recipe is marked as a favorite.
   *
   * @return true if the recipe is a favorite, false otherwise.
   */
  public boolean getFavorite() {
    return favorite;
  }

  /**
   * Sets the recipe's favorite status.
   *
   * @param favorite the new favorite status to set; true if the recipe should be marked 
   *      as a favorite, false otherwise.
   */
  public void setFavorite(boolean favorite) {
    this.favorite = favorite;
  }

  /**
   * Checks if the recipe is vegan.
   *
   * @return true if the recipe is vegan, false otherwise.
   */
  public boolean getVegan() {
    return vegan;
  }

  /**
   * Sets the vegan status of the recipe.
   *
   * @param isVegan the vegan status to set.
   */
  public void setVegan(boolean isVegan) {
    this.vegan = isVegan;
  }

  /**
   * Checks if the recipe is gluten-free.
   *
   * @return true if the recipe is gluten-free, false otherwise.
   */
  public boolean getGlutenFree() {
    return glutenFree;
  }

  /**
   * Sets the gluten-free status of the recipe.
   *
   * @param isGlutenFree the gluten-free status to set.
   */
  public void setGlutenFree(boolean isGlutenFree) {
    this.glutenFree = isGlutenFree;
  }

  /**
   * Checks if the recipe is lactose-free.
   *
   * @return true if the recipe is lactose-free, false otherwise.
   */
  public boolean getLactoseFree() {
    return lactoseFree;
  }

  /**
   * Sets the lactose-free status of the recipe.
   *
   * @param isLactoseFree the lactose-free status to set
   */
  public void setLactoseFree(boolean isLactoseFree) {
    this.lactoseFree = isLactoseFree;
  }

  /**
   * Returns a string representation of the recipe.
   *
   * @return a string representation of the recipe.
   */
  @Override
  public String toString() {
    return "Recipe{" 
            +
            "name='" + name + '\'' 
            +
            ", ingredients=" + ingredients 
            +
            ", originCountry='" + originCountry + '\'' 
            +
            ", type='" + type + '\'' 
            +
            ", description='" + description + '\'' 
            +
            ", favorite=" + favorite 
            +
            ", isVegan=" + vegan 
            +
            ", isGlutenFree=" + glutenFree 
            +
            ", isLactoseFree=" + lactoseFree 
            +
            '}';
  }
}
