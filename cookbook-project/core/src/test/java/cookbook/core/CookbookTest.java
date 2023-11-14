package cookbook.core;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CookbookTest {
  
  private Cookbook cookbook;
  private Recipe recipe1;
  private Recipe recipe2;
  private Recipe recipe3;
  private Recipe recipe;

  @BeforeEach
  public void setUp(){
    cookbook = new Cookbook(); //creating a new cookbook object 
    recipe = new Recipe(); //creates recipe
    recipe.setName("Taco");
    recipe1 = new Recipe(); 
    recipe2 = new Recipe();
    recipe3 = new Recipe();
    recipe1.setName("Pasta");
    recipe2.setName("Pizza");
    recipe3.setName("Pancakes");
  }

  @Test
  @DisplayName("Test if the addRecipe method works properly.")
  public void testAddRecipe(){
    cookbook.addRecipe(recipe); //add recipe på cookbook
    assertTrue(cookbook.getRecipes().contains(recipe)); //asserts that the cookbook contains the recipe
    assertThrows(IllegalArgumentException.class, () -> cookbook.addRecipe(null)); //tests adding null
    assertThrows(IllegalArgumentException.class, () -> cookbook.addRecipe(recipe)); //test adding already existing recipe
    assertEquals(1, cookbook.getRecipes().size()); // tests cookbook size
  }

  @Test
  @DisplayName("Test if the addRecipes method works properly.")
  public void testAddRecipes(){
    Collection<Recipe> recipes = new ArrayList<>(Arrays.asList(recipe1, recipe2, recipe3));
    cookbook.addRecipes(recipes); //add recipe på cookbook

    assertTrue(cookbook.getRecipes().contains(recipe2)); //asserts that the cookbook contains the recipe
    assertThrows(IllegalArgumentException.class, () -> cookbook.addRecipes(new ArrayList<>()));
    assertEquals(3, cookbook.getRecipes().size()); // tests cookbook size
  }

  @Test
  @DisplayName("Test if the removeRecipe method works properly.")
  public void testRemoveRecipe() {
    cookbook.addRecipe(recipe); //adds recipe
    cookbook.removeRecipe(recipe); //removes recipe
    assertFalse(cookbook.getRecipes().contains(recipe)); // tests that cookbook is empty
    assertThrows(IllegalArgumentException.class, () -> cookbook.removeRecipe(recipe)); //cannot remove 'nonexisting' recipe
  }

  @Test
  @DisplayName("Test if the filterRecipes method works properly.")
  public void testFilterRecipes() {
    Recipe testRecipe1 = new Recipe();
    testRecipe1.setName("Pasta");
    testRecipe1.setGlutenFree(true);
    testRecipe1.addIngredient("ingredient1", "200.0");
    cookbook.addRecipe(testRecipe1);
        
    Recipe testRecipe2 = new Recipe();
    testRecipe2.setName("Pizza");
    testRecipe2.addIngredient("ingredient1", "100.0");
    cookbook.addRecipe(testRecipe2);
        
    Recipe testRecipe3 = new Recipe();
    testRecipe3.setName("Pancakes");
    testRecipe3.setGlutenFree(true);
    testRecipe3.addIngredient("ingredient2", "150.0");
    cookbook.addRecipe(testRecipe3);
        
    Predicate<Recipe> ingredientFilter = r -> r.getIngredients().containsKey("ingredient1");
    Collection<Recipe> ingredient1recipes = cookbook.filterRecipes(ingredientFilter);
    assertEquals(2, ingredient1recipes.size());

    Predicate<Recipe> isGlutenFree = r -> r.getGlutenFree();
    Collection<Recipe> glutenFreeRecipes = cookbook.filterRecipes(isGlutenFree);
    assertEquals(2, glutenFreeRecipes.size());
  }
}
