package cookbook.accessdata;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    recipe1.setOriginCountry("Mexico");
    recipe1.setType("Dinner");
    recipe1.setFavorite(true);

    Recipe recipe2 = new Recipe();
    recipe2.setName("Pizza");
    recipe2.setVegan(true);
    recipe2.setType("Dinner");
    recipe2.setOriginCountry("Italy");
    recipe2.setFavorite(true);

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
    List<String> actual = cookBookToCollection(lca.filterByType("Dinner"));
    List<Recipe> expected = testCookbook.getRecipes().stream().filter(r -> r.getType().equals("Dinner")).toList();
    List<Recipe> wrong = testCookbook.getRecipes().stream().filter(r -> r.getType().equals("Breakfast")).toList();
    assertEquals(expected.size(), actual.size());
    assertEquals(0, wrong.size());
    }

  @Test
  public void testFilterByFavorite() {
    List<String> actual = cookBookToCollection(lca.filterByFavorite());
    assertEquals(2, actual.size());
  }

  @Test
  public void testFilterByPreferences() {
    List<String> FFF = cookBookToCollection(lca.filterByPreferences("FFF"));
    List<String> TFF = cookBookToCollection(lca.filterByPreferences("TFF"));
    List<String> FTT = cookBookToCollection(lca.filterByPreferences("FTF"));

    assertEquals("Taco", FFF.iterator().next());
    assertEquals("Pizza", TFF.iterator().next());
    assertEquals("Salad", FTT.iterator().next());
  }

  @Test
  public void testUpdateRecipe() {
    Recipe newRecipe = new Recipe();
    newRecipe.setName("Taco");
    newRecipe.setGlutenFree(true);
    lca.updateRecipe(newRecipe);
    assertEquals(true, lca.fetchCookbook().getRecipes().stream().
        filter(r -> r.getName()
        .equals("Taco"))
        .toList()
        .iterator()
        .next()
        .getGlutenFree());
  }

  @Test 
  public void testRemoveRecipe() {
    assertTrue(lca.removeRecipe("Taco"));
    assertFalse(lca.removeRecipe("Fish"));
  }

  @Test
  public void testAddRecipe() {
    Recipe newRecipe = new Recipe();
    newRecipe.setName("Tomatosoup");
    newRecipe.setVegan(true);
    newRecipe.setOriginCountry("Spain");
    lca.addRecipe(newRecipe);
    assertEquals(4, lca.fetchCookbook().getRecipes().size());
  }

  @Test
  public void testToggleFavorite() {
    lca.toggleFavorite(lca.fetchCookbook().getRecipes().stream()
        .filter(r -> r.getName()
        .equals("Salad"))
        .toList()
        .iterator()
        .next());

    assertTrue(lca.fetchCookbook().getRecipes().stream()
        .filter(r -> r.getName()
        .equals("Salad"))
        .toList()
        .iterator()
        .next()
        .getFavorite());
  }

  private List<String> cookBookToCollection(Cookbook cookbook) {
    return cookbook.getRecipes().stream().map(r->r.getName()).toList();
  }
  
}
