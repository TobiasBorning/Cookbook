package cookbook.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecipeTest {

    private Recipe recipe;

    @BeforeEach
    public void setUp() {
        recipe = new Recipe();
    }

    @Test
    @DisplayName("Test if the setName method for recipes works properly.")
    public void testSetName() {
        recipe.setName("Spaghetti Carbonara");
        assertEquals("Spaghetti Carbonara", recipe.getName());
    }

    @Test
    @DisplayName("Test if the setIngredient method works properly.")
    public void testSetIngredients() {
        Map<String, String> ingredients = new HashMap<>();
        ingredients.put("spaghetti", "200.0");
        ingredients.put("egg", "2.0");
        recipe.setIngredients(ingredients);

        Map<String, String> recipeIngredients = recipe.getIngredients();
        assertEquals(2, recipeIngredients.size());
        assertEquals("200.0", recipeIngredients.get("spaghetti".toString()), "0.001");
        assertEquals("2.0", recipeIngredients.get("egg").toString(), "0.001");
    }

    @Test
    @DisplayName("Test if the setOriginCountry method for recipes works properly.")
    public void testSetOriginCountry() {
        recipe.setOriginCountry("Italy");
        assertEquals("Italy", recipe.getOriginCountry());
    }

    @Test
    @DisplayName("Test if the setDescription method for recipes works properly.")
    public void testSetDescription() {
        recipe.setDescription("A classic Italian pasta dish.");
        assertEquals("A classic Italian pasta dish.", recipe.getDescription());
    }

    @Test
    @DisplayName("Test if the addIngredient and removeIngredient methods for recipes works properly.")
    public void testAddAndRemoveIngredient() {
        recipe.addIngredient("spaghetti", "200.0");
        recipe.addIngredient("egg", "2.0");
        
        Map<String, String> recipeIngredients = recipe.getIngredients();
        assertEquals(2, recipeIngredients.size());
        
        recipe.removeIngredient("spaghetti");
        assertEquals(1, recipeIngredients.size());
        assertEquals(null,recipeIngredients.get("spaghetti"));
    }

    @Test
    @DisplayName("Test if the addIngredient method throws IllegalArgumentException if the ingredient is already added.")
    public void testAddDuplicateIngredient() {
        recipe.addIngredient("spaghetti", "200.0");
        assertThrows(IllegalArgumentException.class, () -> {
            recipe.addIngredient("spaghetti", "150.0"); // This should throw an exception
        });
        
    }

    @Test
    @DisplayName("Test if the removeIngredient method throws IllegalArgumentException if the recipe doesn´t contain the ingredient it wants to remove")
    public void testRemoveNonexistentIngredient() {
      assertThrows(IllegalArgumentException.class, () -> {
        recipe.removeIngredient("cheese"); // "cheese" should not be in the ingredients
      });
        
    }

    @Test
    @DisplayName("Test if the setType method for recipes works properly.")
    public void testSetType() {
        Recipe recipe2 = new Recipe();
        recipe2.setType("nothing");
        recipe.setType("Dinner");
        assertEquals("Dinner", recipe.getType());
        assertEquals("Unknown", recipe2.getType());
    }

    @Test
    @DisplayName("Test if the setFavorite method for recipes works properly.")
    public void testSetFavorite() {
        recipe.setFavorite(true);
        assertEquals(true, recipe.isFavorite());
    }

    @Test
    @DisplayName("Test if the setVegan method for recipes works properly.")
    public void testSetVegan() {
        recipe.setVegan(true);
        assertEquals(true, recipe.isVegan());
    }

    @Test
    @DisplayName("Test if the setLactoseFree method for recipes works properly.")
    public void testSetLactoseFree() {
        recipe.setLactoseFree(true);
        assertEquals(true, recipe.isLactoseFree());
    }

    @Test
    @DisplayName("Test if the setGlutenFree method for recipes works properly.")
    public void testSetGlutenFree() {
        recipe.setGlutenFree(true);
        assertEquals(true, recipe.isGlutenFree());
    }

    @Test
    @DisplayName("Test the empty constructor for recipes.")
    public void testEmptyConstructor() {
        Recipe recipe2 = new Recipe();
        Map<String, String> ingredients = new HashMap<>();
        assertEquals(null, recipe2.getName());
        assertEquals(ingredients, recipe2.getIngredients());
        assertEquals(null, recipe2.getOriginCountry());
        assertEquals("Unknown", recipe2.getType());
        assertEquals(null, recipe2.getDescription());
        assertFalse(recipe2.isFavorite());
        assertFalse(recipe2.isVegan());
        assertFalse(recipe2.isGlutenFree());
        assertFalse(recipe2.isLactoseFree());

    }

    @Test
    @DisplayName("Test the full constructor for recipes.")
    public void testFullConstructor() {
        Map<String, String> ingredients = new HashMap<>();
        ingredients.put("spaghetti", "20.0");
        Recipe recipe2 = new Recipe("Spaghetti Carbonara", ingredients,"Italy", "Dinner", "A classic Italian pasta dish", true, true, false, false);
        assertEquals("Spaghetti Carbonara", recipe2.getName());
        assertEquals(ingredients, recipe2.getIngredients());
        assertEquals("Italy", recipe2.getOriginCountry());
        assertEquals("Dinner", recipe2.getType());
        assertEquals("A classic Italian pasta dish", recipe2.getDescription());
        assertTrue(recipe2.isFavorite());
        assertTrue(recipe2.isVegan());
        assertFalse(recipe2.isGlutenFree());
        assertFalse(recipe2.isLactoseFree());
    }
}


