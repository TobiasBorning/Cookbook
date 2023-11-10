package cookbook.accessdata;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import static org.junit.jupiter.api.TestInstance.Lifecycle;

import com.google.gson.Gson;

import cookbook.core.Cookbook;
import cookbook.core.Recipe;
import cookbook.json.CookbookHandler;

@TestInstance(Lifecycle.PER_CLASS)
public class LocalCookbookAccessTest {

  private CookbookHandler ch = new CookbookHandler();
  private Cookbook savedCookbook;
  private Cookbook testCookbook;
  private LocalCookbookAccess lca;

  @BeforeAll
  public void saveCookbook() throws IOException {
    savedCookbook = ch.readFromFile("../persistence/cookbook.json");
  }

  @AfterAll
  public void loadSavedCookbook() throws IOException {
    ch.writeToFile(savedCookbook, "../persistence/cookbook.json");
  }

  @BeforeEach
  private void setup() throws FileNotFoundException {
    lca = new LocalCookbookAccess();

    Recipe recipe1 = new Recipe();
    recipe1.setName("Taco");
    recipe1.setGlutenFree(true);
    recipe1.setOriginCountry("Mexico");

    Recipe recipe2 = new Recipe();
    recipe2.setName("Pizza");
    recipe2.setVegan(true);
    recipe2.setOriginCountry("Italy");

    Recipe recipe3 = new Recipe();
    recipe3.setName("Salad");
    recipe3.setGlutenFree(true);
    recipe3.setLactoseFree(true);
    recipe3.setOriginCountry("Norway");
    

    Cookbook cookbook = new Cookbook();
    cookbook.addRecipe(recipe1);
    cookbook.addRecipe(recipe2);
    cookbook.addRecipe(recipe3);
    this.testCookbook = cookbook;
    ch.writeToFile(testCookbook, "../persistence/cookbook.json");
  }

  
  @Test 
  public void testFetchCookbook() throws FileNotFoundException {
    Cookbook cookbook = lca.fetchCookbook();
    assertEquals(cookBookToCollection(testCookbook), cookBookToCollection(cookbook));
  }
  
  @Test
  public void testSearchRecipe() {
    List<String> all = cookBookToCollection(testCookbook);
    List<String> expected = all.stream().filter(r -> r.equals("Taco")).toList();
    List<String> actual = cookBookToCollection(lca.searchRecipe("Taco"));
    assertEquals(expected, actual);
  }

  @Test
  public void testFilterByOrigin() {
    List<String> actual = cookBookToCollection(lca.filterByOrigin("Italy"));
    List<String> wrong = cookBookToCollection(lca.filterByOrigin("Spain"));

    assertEquals("Pizza", actual.iterator().next());
    assertFalse(wrong.iterator().hasNext());
  }

  @Test
  public void testFilterByType() {
    List<Recipe> expected = testCookbook.getRecipes().stream().filter(r -> r.getType().equals("Dinner")).toList();
    }

  @Test
  public void testFilterByFavorite() {
    
  }

  @Test
  public void testFilterByPreferences() {
    
  }

  @Test
  public void testUpdateRecipe() {
    
  }

  @Test
  public void testAddRecipe() {
    
  }

  @Test
  public void testRemoveRecipe() {
    
  }

  @Test
  public void testSaveCookbook() {
    
  }

  @Test
  public void testToggleFavorite() {
    
  }

  private List<String> cookBookToCollection(Cookbook cookbook) {
    return cookbook.getRecipes().stream().map(r->r.getName()).toList();
  }
  
}
