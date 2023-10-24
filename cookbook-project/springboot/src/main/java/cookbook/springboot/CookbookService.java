package cookbook.springboot;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cookbook.core.Cookbook;
import cookbook.core.Recipe;



@Service
public class CookbookService {

    private Gson gson;
    private static final String COOKBOOK_PATH = "../persistence/remote-cookbook.json";

    
    public CookbookService() {
        System.out.println("CookbookService constructor");
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        readCookbook();
    }

    public Cookbook readCookbook() {
        try (FileReader reader = new FileReader(COOKBOOK_PATH)){
            return gson.fromJson(reader, Cookbook.class);
        } catch (IOException e) {
            throw new RuntimeException("Could not find cookbook file.",e);
        }
    }

    public void writeCookbook(Cookbook cookbook) {
        try (FileWriter writer = new FileWriter(COOKBOOK_PATH)){
            gson.toJson(cookbook, writer);
        } catch (IOException e) {
            throw new RuntimeException("Could not write cookbook file.",e);
        }
    }

    public void updateCookbook(Cookbook cookbook) {
        //can add validation here
        writeCookbook(cookbook);
    }

    public void addRecipe(String recipeJson, Cookbook cookbook) {
        Recipe recipe = gson.fromJson(recipeJson, Recipe.class);
        cookbook.addRecipe(recipe);
        updateCookbook(cookbook);
    }

    public Cookbook getRecipe(String name, Cookbook cookbook) {
        Cookbook tmpCookbook = new Cookbook();
        for (Recipe recipe : cookbook.getRecipes()) {
            if (recipe.getName().toLowerCase().contains(name.toLowerCase())) {
                tmpCookbook.addRecipe(recipe);
            }
        }
        return tmpCookbook;
    }

    public Cookbook filterByOrigin(String origin, Cookbook cookbook) {
        Cookbook tmpCookbook = new Cookbook();
        for (Recipe recipe : cookbook.getRecipes()) {
            if (recipe.getOriginCountry().equals(origin)) {
                tmpCookbook.addRecipe(recipe);
            }
        }
        return tmpCookbook;
    }

    public Cookbook filterByType(String type, Cookbook cookbook) {
        Cookbook tmpCookbook = new Cookbook();
        for (Recipe recipe : cookbook.getRecipes()) {
            if (recipe.getType().toLowerCase().equals(type.toLowerCase())) {
                tmpCookbook.addRecipe(recipe);
            }
        }
        return tmpCookbook;
    }

    public Cookbook filterByFavorite(Cookbook cookbook) {
        Cookbook tmpCookbook = new Cookbook();
        for (Recipe recipe : cookbook.getRecipes()) {
            if (recipe.isFavorite()) {
                tmpCookbook.addRecipe(recipe);
            }
        }
        return tmpCookbook;
    }

    public Cookbook filterByPreferences(String vlg, Cookbook cookbook) { //GLV = Gluten, Lactose, Vegan
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

    public void deleteRecipe(String recipeName, Cookbook cookbook) {
        System.out.println("Running deleteRecipe in CookbookService");
        for (Recipe recipe : cookbook.getRecipes()) {
            if (recipe.getName().equals(recipeName)) {
                cookbook.removeRecipe(recipe);
            }
        }
        updateCookbook(cookbook);
    }

}
