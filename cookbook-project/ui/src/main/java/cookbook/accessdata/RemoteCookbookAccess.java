package cookbook.accessdata;

import java.util.Collection;
import java.util.Map;
import java.util.function.Predicate;

import cookbook.core.Recipe;

public class RemoteCookbookAccess implements CookbookAccess {
    public RemoteCookbookAccess() {
        
    }

    @Override
    public void addRecipe() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addRecipe'");
    }

    @Override
    public void removeRecipe(Recipe recipe) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeRecipe'");
    }

    @Override
    public Collection<Recipe> filterRecipies(Predicate<Recipe> pred) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'filterRecipies'");
    }

    @Override
    public Collection<Recipe> getRecipes() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRecipes'");
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getName'");
    }

    @Override
    public String getOriginCountry() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOriginCountry'");
    }

    @Override
    public String getDescription() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDescription'");
    }

    @Override
    public Map<String, String> getIngredients() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getIngredients'");
    }

    @Override
    public void setName(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setName'");
    }

    @Override
    public void setOriginCountry(String originCountry) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setOriginCountry'");
    }

    @Override
    public void setIngredients(Map<String, String> ingredients) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setIngredients'");
    }

    @Override
    public void setDescription(String description) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setDescription'");
    }

    @Override
    public void addIngredient(String ingredient, String amount) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addIngredient'");
    }
}
