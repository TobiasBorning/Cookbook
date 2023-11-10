package cookbook.accessdata;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;

import com.google.gson.Gson;

import cookbook.core.Cookbook;
import cookbook.core.Recipe;
import cookbook.json.CookbookHandler;


@TestInstance(Lifecycle.PER_CLASS)
public class RemoteCookbookAccessTest {
  
  private Gson gson;
  private CookbookHandler handler = new CookbookHandler();
  private Cookbook savedCookbook;
  private Cookbook testCookbook;
  private Recipe recipe1;
  private Recipe recipe2;
  private Recipe recipe3;
  private RemoteCookbookAccess access = new RemoteCookbookAccess();
  
  WireMockServer wireMockServer;

  @BeforeAll
  public void saveCookbook() throws IOException {
    savedCookbook = handler.readFromFile("../persistence/remote-cookbook.json");
    wireMockServer = new WireMockServer(options().port(8080));
    wireMockServer.start(); 
    WireMock.configureFor("localhost", wireMockServer.port());
  }

  
  @AfterAll
  public void loadSavedCookbook() throws IOException {
    handler.writeToFile(savedCookbook, "../persistence/remote-cookbook.json");
    wireMockServer.stop();
  }

  @BeforeEach
  public void setup() throws Exception {
    gson = new Gson();
    //Create taco recipe
    recipe1 = new Recipe();
    recipe1.setName("Taco");
    recipe1.setFavorite(true);
    recipe1.setGlutenFree(true);
    recipe1.setType("Dinner");
    recipe1.setOriginCountry("Mexico");
    //create pizza recipe
    recipe2 = new Recipe();
    recipe2.setName("Pizza");
    recipe2.setFavorite(false);
    recipe2.setGlutenFree(false);
    recipe2.setType("Breakfast");
    recipe2.setOriginCountry("Italy");
    //add all recipes to cookbook in mock api
    testCookbook = new Cookbook();
    testCookbook.addRecipe(recipe1);
    testCookbook.addRecipe(recipe2);
  }

  @Test
  public void testFetchCookbook() {
    stubFor(get("/api/cookbook")
            .willReturn(aResponse()
            .withHeader("Content-Type", "application/json")
            .withBody(gson.toJson(testCookbook))
            .withStatus(200)));
    assertEquals(getRecipeNames(testCookbook), getRecipeNames(access.fetchCookbook()));

    stubFor(get("/api/cookbook")
      .willReturn(aResponse()
      .withHeader("Content-Type", "application/json")
      .withBody(gson.toJson(testCookbook))
      .withStatus(404)));

    assertThrows(RuntimeException.class, () ->{
      access.fetchCookbook();
    });
  }

  @Test 
  public void testAddRecipe() {
    recipe3 = new Recipe();
    recipe3.setName("Pasta");
    stubFor(post("/api/cookbook")
            .willReturn(aResponse()
            .withHeader("Content-Type", "application/json")
            .withBody(gson.toJson(recipe3))
            .withStatus(200)));
    assertDoesNotThrow(()->{
      access.addRecipe(recipe3);
    }); 
    stubFor(post("/api/cookbook")
            .willReturn(aResponse()
            .withHeader("Content-Type", "application/json")
            .withBody(gson.toJson(recipe3))
            .withStatus(404)));
    assertThrows(RuntimeException.class, () ->{
      access.addRecipe(recipe3);
    });
  }

  @Test
  public void testRemoveRecipe() {
    // test deleting recipe that exists
    recipe3 = new Recipe();
    recipe3.setName("Pasta");
    stubFor(delete("/api/cookbook/recipe/"+recipe3.getName())
            .willReturn(aResponse()
            .withHeader("Content-Type", "application/json")
            .withStatus(200)));
    assertTrue(access.removeRecipe(recipe3.getName()));
    // test deleting recipe that does not exist
    recipe3 = new Recipe();
    recipe3.setName("Chili con carne");
    stubFor(delete("/api/cookbook/recipe/"+recipe3.getName())
            .willReturn(aResponse()
            .withHeader("Content-Type", "application/json")
            .withStatus(404)));
    assertFalse(access.removeRecipe(recipe3.getName()));
  }

  @Test
  public void testFilteringByOrigin() {
    // test filtering by exsisting origin
    String validOrigin = "Mexico";
    stubFor(get("/api/cookbook/origin/" + validOrigin)
            .willReturn(aResponse()
            .withHeader("Content-Type", "application/json")
            .withStatus(200)));
    access.filterByOrigin(validOrigin);
    // test filtering by origin that does not exist
    String invalidOrigin = "Canada";
    stubFor(get("/api/cookbook/origin/" + invalidOrigin)
            .willReturn(aResponse()
            .withHeader("Content-Type", "application/json")
            .withStatus(404)));
    assertThrows(RuntimeException.class, () ->{
      access.filterByOrigin(invalidOrigin);
    });
  }

  @Test
  public void testFilteringByType() {
    // test filtering by existing type
    String validType = "Dinner";
    stubFor(get("/api/cookbook/type/" + validType)
            .willReturn(aResponse()
            .withHeader("Content-Type", "application/json")
            .withStatus(200)));
    assertDoesNotThrow(() -> {
      access.filterByType(validType);
    });
    // test filtering by type that does not exist
    String invalidType = "Brunch";
    stubFor(get("/api/cookbook/type/" + invalidType)
            .willReturn(aResponse()
            .withHeader("Content-Type", "application/json")
            .withStatus(404)));
    assertThrows(RuntimeException.class, () ->{
      access.filterByType(invalidType);
    });
  }

  @Test
  public void testFilteringByPreferences() {
    // test filtering by existing preferences
    String validPreference = "TFF";
    stubFor(get("/api/cookbook/preferences/" + validPreference)
            .willReturn(aResponse()
            .withHeader("Content-Type", "application/json")
            .withStatus(200)));
    assertDoesNotThrow(() -> {
      access.filterByPreferences(validPreference);
    });
    // test filtering by preferences that does not exist
    String invalidPreference = "XYZ";
    stubFor(get("/api/cookbook/preferences/" + invalidPreference)
            .willReturn(aResponse()
            .withHeader("Content-Type", "application/json")
            .withStatus(404)));
    assertThrows(RuntimeException.class, () ->{
      access.filterByPreferences(invalidPreference);
    });
  }

  @Test
  public void testFilteringByFavorite() {
    // test filtering by favorite
    stubFor(get("/api/cookbook/favorites")
            .willReturn(aResponse()
            .withHeader("Content-Type", "application/json")
            .withStatus(200)));
    assertDoesNotThrow(() -> {
      access.filterByFavorite();
    });
    stubFor(get("/api/cookbook/favorites")
            .willReturn(aResponse()
            .withHeader("Content-Type", "application/json")
            .withStatus(404)));
    assertThrows(RuntimeException.class, () ->{
      access.filterByFavorite();
    });
  }

  @Test
  public void testUpdatingRecipe() {
    // test updating recipe
    recipe3 = new Recipe();
    recipe3.setName("Pasta");
    stubFor(put("/api/cookbook/recipe/"+recipe3.getName())
            .willReturn(aResponse()
            .withHeader("Content-Type", "application/json")
            .withStatus(200)));
    assertDoesNotThrow(() ->{
      access.updateRecipe(recipe3);
    });
    // test not being able to update recipe
    stubFor(put("/api/cookbook/recipe/"+recipe3.getName())
            .willReturn(aResponse()
            .withHeader("Content-Type", "application/json")
            .withStatus(404)));
    assertThrows(RuntimeException.class, () ->{
      access.updateRecipe(recipe3);
    });
  }

  @Test
  public void testFavoritingRecipe() {
    // test favoriting recipe
    recipe3 = new Recipe();
    recipe3.setName("Pasta");
    stubFor(put("/api/cookbook/favorite/"+recipe3.getName())
            .willReturn(aResponse()
            .withHeader("Content-Type", "application/json")
            .withStatus(200)));
    assertDoesNotThrow(() ->{
      access.toggleFavorite(recipe3);
    });
    // test not being able to favorite recipe
    stubFor(put("/api/cookbook/favorite/"+recipe3.getName())
            .willReturn(aResponse()
            .withHeader("Content-Type", "application/json")
            .withStatus(404)));
    assertThrows(RuntimeException.class, () ->{
      access.toggleFavorite(recipe3);
    });
  }

  @Test
  public void testSearching() {
    // test searching for recipe
    String validSearch = "Pizza";
    stubFor(get("/api/cookbook/search/" + validSearch)
            .willReturn(aResponse()
            .withHeader("Content-Type", "application/json")
            .withStatus(200)));
    assertDoesNotThrow(() -> {
      access.searchRecipe(validSearch);
    });
    // test not being able to search for recipe
    String invalidSearch = "Chili con carne";
    stubFor(get("/api/cookbook/search/" + invalidSearch)
            .willReturn(aResponse()
            .withHeader("Content-Type", "application/json")
            .withStatus(404)));
    assertThrows(RuntimeException.class, () ->{
      access.searchRecipe(invalidSearch);
    });
  }
  
  private List<String> getRecipeNames(Cookbook cookbook) {
    return cookbook.getRecipes().stream().map(r -> r.getName()).toList();
  }
}
