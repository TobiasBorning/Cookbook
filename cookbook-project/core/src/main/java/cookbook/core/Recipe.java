package cookbook.core;

import java.util.HashMap;
import java.util.Map;

public class Recipe {
    public String name;
    Map<String,Double> ingredients = new HashMap<>();
    String originCountry;
    String description;

    public Recipe(String name, Map<String, Double> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }

    public Recipe(String name) {
        this.name = name;
    }

    public Recipe() {
        
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIngredients(Map<String, Double> ingredients) {
        this.ingredients = ingredients;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return this.name;
    }

    public Map<String,Double> getIngredients() {
        return new HashMap<>(ingredients);
    }

    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Adding and removing ingredients
    // Could implement unit feature
    
    public void addIngredient(String ingredient, Double amount) { 
        if (ingredients.keySet().contains(ingredient)) {
            throw new IllegalArgumentException("Ingredient allready in map");
        }
        ingredients.put(ingredient, amount);
    }
    
    public void removeIngredient(String ingredient) { 
      if (!ingredients.keySet().contains(ingredient)) {
          throw new IllegalArgumentException("Ingredient allready in map");
      }
      ingredients.remove(ingredient);
  }

}
