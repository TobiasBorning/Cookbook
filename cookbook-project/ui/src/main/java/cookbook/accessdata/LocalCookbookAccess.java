package cookbook.accessdata;

import java.io.FileNotFoundException;
import java.util.Collection;

import cookbook.core.Cookbook;
import cookbook.core.Recipe;
import cookbook.json.CookbookHandler;

public class LocalCookbookAccess implements CookbookAccess {

  private CookbookHandler ch = new CookbookHandler();
  private final static String path = "../persistence/default-cookbook.json";

  @Override
  public Cookbook fetchCookbook() {
    try {
      return ch.readFromFile(path);
    } catch (FileNotFoundException e) { 
      return null;
    }
  }

  @Override
  public Cookbook searchRecipe(String recipeName) {
      Collection<Recipe> searched = fetchCookbook().filterRecipies(recipe -> recipe.getName().toLowerCase().contains(recipeName.toLowerCase()));
      Cookbook tmpCookbook = new Cookbook();
      for (Recipe recipe : searched) {
        tmpCookbook.addRecipe(recipe);
      }
      return tmpCookbook;
  }

  @Override
  public Cookbook filterByOrigin(String origin) {
      Collection<Recipe> searched = fetchCookbook().filterRecipies(recipe -> recipe.getOriginCountry().equals(origin));
      Cookbook tmpCookbook = new Cookbook();
      for (Recipe recipe : searched) {
        tmpCookbook.addRecipe(recipe);
      }
      return tmpCookbook;
  }

  @Override
  public Cookbook filterByType(String type) {
    Collection<Recipe> searched = fetchCookbook().filterRecipies(recipe -> recipe.getType().equals(type));
      Cookbook tmpCookbook = new Cookbook();
      for (Recipe recipe : searched) {
        tmpCookbook.addRecipe(recipe);
      }
      return tmpCookbook;
  }

  @Override
  public Cookbook filterByFavorite() {
    Collection<Recipe> searched = fetchCookbook().filterRecipies(recipe -> recipe.isFavorite() == true);
    Cookbook tmpCookbook = new Cookbook();
    for (Recipe recipe : searched) {
      tmpCookbook.addRecipe(recipe);
    }
    return tmpCookbook;
  }

  @Override
  public Cookbook filterByPreferences(String vlg) {
    
    Cookbook tmpCookbook = new Cookbook();
        
    boolean gluten = vlg.charAt(2) == 'T';
    boolean lactose = vlg.charAt(1) == 'T';
    boolean vegan = vlg.charAt(0) == 'T';

    for (Recipe recipe : fetchCookbook().getRecipes()) {
        if (!((!recipe.isGlutenFree() && gluten) || (!recipe.isLactoseFree() && lactose) || (!recipe.isVegan() && vegan))) {
            tmpCookbook.addRecipe(recipe);
        }
    }

    return tmpCookbook;
  }

  @Override
  public void updateRecipe(Recipe recipe) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateRecipe'");
  }

  @Override
  public void removeRecipe(String recipeName) {
    Cookbook tmpCookbook = fetchCookbook();
    for (Recipe recipe : fetchCookbook().getRecipes()) {
      if (recipe.getName().equals(recipeName)) {
        tmpCookbook.removeRecipe(recipe);
        break;
      }
    }
    saveCookbook(tmpCookbook);
  }

  @Override
  public void addRecipe(Recipe recipe) {
    Cookbook tmpCookbook = fetchCookbook();
    tmpCookbook.addRecipe(recipe);
    saveCookbook(tmpCookbook);
  }

  private void saveCookbook(Cookbook cookbook) {
    try {
      ch.writeToFile(cookbook, path);
    } catch (FileNotFoundException e) {
      throw new RuntimeException("File not found");
    }
  }

  
}
