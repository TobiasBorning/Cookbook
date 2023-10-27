package cookbook.springboot;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cookbook.core.Cookbook;
import cookbook.core.Recipe;



/**
 * Service class responsible for managing cookbook operations.
 */
@Service
public class CookbookService {

    private Gson gson;
    private static final String COOKBOOK_PATH = "../persistence/remote-cookbook.json";

    /**
     * Constructs a new CookbookService.
     */
    public CookbookService() {
        System.out.println("CookbookService constructor");
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        readCookbook();
    }

    /**
     * Reads the cookbook from the file.
     *
     * @return the cookbook.
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
     */
    public void addRecipe(final String recipeJson, final Cookbook cookbook) {
        Recipe recipe = gson.fromJson(recipeJson, Recipe.class);
        cookbook.addRecipe(recipe);
        updateCookbook(cookbook);
    }

    /**
     * Removes a recipe from the cookbook.
     *
     * @param recipeName the name of the recipe to remove.
     * @param cookbook the cookbook to remove the recipe from.
     */
    public ResponseEntity<Void> deleteRecipe(final String recipeName, final Cookbook cookbook) {
        System.out.println("Running deleteRecipe in CookbookService");
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
            if (recipe.isFavorite()) {
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
            if (!((!recipe.isGlutenFree() && gluten) || (!recipe.isLactoseFree() && lactose) || (!recipe.isVegan() && vegan))) {
                tmpCookbook.addRecipe(recipe);
            }
        }
        return tmpCookbook;
    }
    
    /**
     * Filters recipes based on user preferences.
     *
     * @param origin the country of origin to filter by.
     * @param type the type to filter by.
     * @param vlg a string representing user preferences.
     * @param favorites whether to filter by favorite recipes.
     * @param cookbook the cookbook to filter.
     * @return a cookbook containing the matching recipes.
     */
    public Cookbook masterFilter(final String origin, final String type, final String vlg, final String favorites, final Cookbook cookbook) {
        Cookbook tmpCookbook = new Cookbook();
        tmpCookbook = filterByOrigin(origin, cookbook);
        tmpCookbook = filterByType(type, tmpCookbook);
        tmpCookbook = filterByPreferences(vlg, tmpCookbook);
        if (favorites.equals("T")) {
            tmpCookbook = filterByFavorite(tmpCookbook);
        }
        return tmpCookbook;
    }

    /**
     * Sets a recipe as favorite.
     *
     * @param recipeName the name of the recipe to set as favorite.
     * @param cookbook the cookbook to update.
     * @return the updated Recipe.
     */
    public Recipe toggleFavorite(final String recipeName, final Cookbook cookbook) {
        Recipe tmpRecipe = null;
        for (Recipe recipe : cookbook.getRecipes()) {
            if (recipe.getName().equals(recipeName)) {
                recipe.setFavorite(!recipe.isFavorite());
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
     * @return the updated Recipe.
     */
    public Recipe updateRecipe(final String recipeName, final String recipeJson, final Cookbook cookbook) {
        Recipe tmpRecipe = null;
        recipeName.replace("%20", " ");
        for (Recipe recipe : cookbook.getRecipes()) {
            if (recipe.getName().equals(recipeName)) {
                System.out.println(recipeName + " = " + recipe.getName());
                tmpRecipe = gson.fromJson(recipeJson, Recipe.class);
                recipe.setName(tmpRecipe.getName());
                recipe.setOriginCountry(tmpRecipe.getOriginCountry());
                recipe.setType(tmpRecipe.getType());
                recipe.setIngredients(tmpRecipe.getIngredients());
                recipe.setGlutenFree(tmpRecipe.isGlutenFree());
                recipe.setLactoseFree(tmpRecipe.isLactoseFree());
                recipe.setVegan(tmpRecipe.isVegan());
                recipe.setFavorite(tmpRecipe.isFavorite());
            }
        }
        updateCookbook(cookbook);
        return tmpRecipe;
    }
}
