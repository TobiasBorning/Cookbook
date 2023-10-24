package cookbook.accessdata;

import cookbook.core.Cookbook;
import cookbook.core.Recipe;

public interface CookbookAccess {

    public Cookbook fetchCookbook();

    public Cookbook searchRecipe(String recipeName);

    public Cookbook filterByOrigin(String origin);

    public Cookbook filterByType(String type);

    public Cookbook filterByFavorite();

    public Cookbook filterByPreferences(String vlg);

    public void updateRecipe(Recipe recipe);

    public void removeRecipe(String recipeName);

    public void addRecipe(Recipe recipe);
}
