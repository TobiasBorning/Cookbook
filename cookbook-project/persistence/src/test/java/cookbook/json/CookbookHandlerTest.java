package cookbook.json;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import cookbook.core.Cookbook;
import cookbook.core.Recipe;

public class CookbookHandlerTest {

  private CookbookHandler cookbookHandler;
  private Recipe pizzaRecipe;
  private Cookbook cookbook;
  private String path;


  @BeforeEach
  public void setUp() {
    cookbookHandler = new CookbookHandler();
    cookbook = new Cookbook();
    pizzaRecipe = new Recipe();
    pizzaRecipe.setName("Pizza");
    pizzaRecipe.addIngredient("cheese", "200.0");
    pizzaRecipe.addIngredient("pizzaDough", "300.0");
    pizzaRecipe.addIngredient("tomatoSauce", "150.0");
    pizzaRecipe.addIngredient("pepperoni", "100.0");
    pizzaRecipe.addIngredient("mushrooms", "50.0");
    pizzaRecipe.addIngredient("onions", "30.0");
    cookbook.addRecipe(pizzaRecipe); 
    path = "test.json";     
  }

  @Test
  @DisplayName("Test if it reads and writes to file properly.")
  public void testWriteAndRead() {
    try {
      cookbookHandler.writeToFile(cookbook,path);
      Cookbook cookbookRead = cookbookHandler.readFromFile(path);
      Boolean fail = false;
      for (Recipe recipe : cookbookRead.getRecipes()) {
          if (!cookbook.getRecipes().stream().map(Recipe::getName).toList().contains(recipe.getName())) {
            fail = true;
          }
      }
      assertFalse(fail);
    } catch (FileNotFoundException e) {
      fail("File not found");
    }
  }

  @Test
  @DisplayName("Test if the readFromFile method throws FileNotFoundException if the file does not excist.")
  public void testThrowsExceptionReadFromInvalidFile() {
    assertThrows(FileNotFoundException.class, () -> {
      cookbookHandler.readFromFile("notAFile.json");
    });
  }
    
}
