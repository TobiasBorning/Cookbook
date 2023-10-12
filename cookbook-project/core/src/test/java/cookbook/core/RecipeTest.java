package cookbook.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
}
