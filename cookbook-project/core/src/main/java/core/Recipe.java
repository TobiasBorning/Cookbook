package core;

import java.util.HashMap;
import java.util.Map;

public class Recipe {
    String name;
    Map<Ingredient,Double> ingredients = new HashMap<>();
    String originCountry;
    String description;

    public Recipe(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public Map<Ingredient,Double> getIngredients() {
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
    
    public void addIngredient(Ingredient ingredient, Double amount) { 
        if (ingredients.keySet().contains(ingredient)) {
            throw new IllegalArgumentException("Ingredient allready in map");
        }
        ingredients.put(ingredient, amount);
    }
    
    public void removeIngredient(Ingredient ingredient) { 
      if (!ingredients.keySet().contains(ingredient)) {
          throw new IllegalArgumentException("Ingredient allready in map");
      }
      ingredients.remove(ingredient);
  }

}
