package cookbook.accessdata;

import java.util.Collection;
import java.util.Map;
import java.util.function.Predicate;

import cookbook.core.Recipe;

public interface CookbookAccess {

  public void addRecipe();

  public void removeRecipe(Recipe recipe);

  public Collection<Recipe> filterRecipies(Predicate<Recipe> pred);

  public Collection<Recipe> getRecipes();

  public String getName();

  public String getOriginCountry();

  public String getDescription();

  public Map<String, String> getIngredients();

  public void setName(String name);

  public void setOriginCountry(String originCountry);

  public void setIngredients(Map<String, String> ingredients);

  public void setDescription(String description);

  public void addIngredient(String ingredient, String amount);
    
}
