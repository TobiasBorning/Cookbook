package core;

import java.util.HashMap;
import java.util.Map;

public class Recipie {
    Map<Ingredient,Double> ingredients = new HashMap<>();
    String originCountry;
    String description;

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
