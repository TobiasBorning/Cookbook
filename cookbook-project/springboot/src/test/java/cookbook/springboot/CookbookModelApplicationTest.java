package cookbook.springboot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.util.Iterator;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import com.google.gson.Gson;

import cookbook.core.Cookbook;
import cookbook.core.Recipe;
import cookbook.json.CookbookHandler;

@WebMvcTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS) //for å kunne bruke @beforeAll non static
@ContextConfiguration(classes = { CookbookController.class,
    CookbookService.class, CookbookModelApplication.class,
    GsonHttpMessageConverter.class }) // la til gsonHttpMessageConverter for å kunne konvertere fra gson
@AutoConfigureMockMvc
public class CookbookModelApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    private Gson gson;
    private CookbookHandler handler = new CookbookHandler();
    private Cookbook savedCookbook;
    private Recipe recipe1;
    private Recipe recipe2;
    private Recipe recipe3;
    private Iterator<Recipe> it;

    @BeforeAll
    public void saveCookbook() throws IOException {
        savedCookbook = handler.readFromFile("../persistence/remote-cookbook.json");
    }

    @AfterAll
    public void loadSavedCookbook() throws IOException {
        handler.writeToFile(savedCookbook, "../persistence/remote-cookbook.json");
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
        //create pasta recipe for deletion
        recipe3 = new Recipe();
        recipe3.setName("Pasta");
        //add all recipes to cookbook in mock api
        setupDefaultCookbook();
    }

    @Test
    public void testGetCookbook() throws Exception {
        //get cookbook
        assertEquals(2, get("/api/cookbook").getRecipes().size());
        it = get("/api/cookbook").getRecipes().iterator();
        assertEquals("Taco", it.next().getName());
        assertEquals("Pizza", it.next().getName());
    }

    @Test
    public void testDeleteRecipe() throws Exception{
        //add pasta
        mockMvc.perform(MockMvcRequestBuilders.post("/api/cookbook")
            .accept(MediaType.APPLICATION_JSON)
            .content(gson.toJson(this.recipe3)))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();
        
        assertEquals(3, get("/api/cookbook").getRecipes().size());
        it = get("/api/cookbook").getRecipes().iterator();
        assertEquals("Taco", it.next().getName());
        assertEquals("Pizza", it.next().getName());
        assertEquals("Pasta", it.next().getName());
        //remove recipe3(pasta)
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/cookbook/recipe/" + this.recipe3.getName())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();
        assertEquals(2, get("/api/cookbook").getRecipes().size());
        it = get("/api/cookbook").getRecipes().iterator();
        assertEquals("Taco", it.next().getName());
        assertEquals("Pizza", it.next().getName());

        //test delete non existing recipe
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/cookbook/recipe/Not%20a%20recipe")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andReturn();
    }

    @Test
    public void testFavorite() throws Exception{
        assertEquals(1, get("/api/cookbook/favorites").getRecipes().size());
        it = get("/api/cookbook/favorites").getRecipes().iterator();
        assertEquals("Taco", it.next().getName());
        //toggle taco favorite false
        mockMvc.perform(MockMvcRequestBuilders.put("/api/cookbook/favorite/" + this.recipe1.getName())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();
        assertEquals(0, get("/api/cookbook/favorites").getRecipes().size());
        //toggle taco favorite true
        mockMvc.perform(MockMvcRequestBuilders.put("/api/cookbook/favorite/" + this.recipe1.getName())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();
        assertEquals(1, get("/api/cookbook/favorites").getRecipes().size());
    }

    @Test
    public void testUpdateRecipe() throws Exception{
        //update taco recipe
        Recipe storedRecipe = recipe1;
        recipe1.setFavorite(false);
        recipe1.setGlutenFree(false);
        recipe1.setType("Breakfast");
        recipe1.setOriginCountry("Norway");
        //update taco recipe
        mockMvc.perform(MockMvcRequestBuilders.put("/api/cookbook/recipe/" + this.recipe1.getName())
            .accept(MediaType.APPLICATION_JSON)
            .content(gson.toJson(this.recipe1)))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();
        //check if taco recipe is updated
        assertEquals(1, get("/api/cookbook/search/Taco").getRecipes().size());
        it = get("/api/cookbook/search/Taco").getRecipes().iterator();
        Recipe taco = it.next();
        assertEquals("Taco", taco.getName());
        assertEquals("Breakfast", taco.getType());
        assertEquals("Norway", taco.getOriginCountry());
        assertEquals(false, taco.isFavorite());
        assertEquals(false, taco.isGlutenFree());
        //revert taco recipe
        mockMvc.perform(MockMvcRequestBuilders.put("/api/cookbook/recipe/" + this.recipe1.getName())
            .accept(MediaType.APPLICATION_JSON)
            .content(gson.toJson(storedRecipe)))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();
    }
        
    @Test
    public void testOriginFilter() {
        //check origin country
        assertEquals(1, get("/api/cookbook/origin/Mexico").getRecipes().size());
        it = get("/api/cookbook/origin/Mexico").getRecipes().iterator();
        assertEquals("Taco", it.next().getName());

        assertEquals(1, get("/api/cookbook/origin/Italy").getRecipes().size());
        it = get("/api/cookbook/origin/Italy").getRecipes().iterator();
        assertEquals("Pizza", it.next().getName());
    }

    @Test
    public void testPreferenceFilter() {
        //check gluten free
        assertEquals(1, get("/api/cookbook/preferences/FFT").getRecipes().size());
        it = get("/api/cookbook/preferences/FFT").getRecipes().iterator();
        assertEquals("Taco", it.next().getName());
        //check no preferences
        assertEquals(2, get("/api/cookbook/preferences/FFF").getRecipes().size());
        it = get("/api/cookbook/preferences/FFF").getRecipes().iterator();
        assertEquals("Taco", it.next().getName());
        assertEquals("Pizza", it.next().getName());
    }

    @Test
    public void testTypeFilter() {
        //check type
        assertEquals(1, get("/api/cookbook/type/Dinner").getRecipes().size());
        it = get("/api/cookbook/type/Dinner").getRecipes().iterator();
        assertEquals("Taco", it.next().getName());
        assertEquals(1, get("/api/cookbook/type/Breakfast").getRecipes().size());
        it = get("/api/cookbook/type/Breakfast").getRecipes().iterator();
        assertEquals("Pizza", it.next().getName());
        assertEquals(0, get("/api/cookbook/type/Lunch").getRecipes().size());
        it = get("/api/cookbook/type/Lunch").getRecipes().iterator();
        assertFalse(it.hasNext());
    }

    @Test
    public void testSearch() {
        //check search
        assertEquals(1, get("/api/cookbook/search/Taco").getRecipes().size());
        it = get("/api/cookbook/search/Taco").getRecipes().iterator();
        assertEquals("Taco", it.next().getName());
        assertEquals(1, get("/api/cookbook/search/Pizza").getRecipes().size());
        it = get("/api/cookbook/search/Pizza").getRecipes().iterator();
        assertEquals("Pizza", it.next().getName());
        assertEquals(0, get("/api/cookbook/search/No%20valid%20recipe").getRecipes().size());
        it = get("/api/cookbook/search/Pasta").getRecipes().iterator();
        assertFalse(it.hasNext());
    }
    

    private Cookbook get(String url) {
        String jsonCookbook;
        try {
            jsonCookbook = mockMvc.perform(MockMvcRequestBuilders.get(url)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        } catch (Exception e) {
            throw new RuntimeException("Could not fetch cookbook",e);
        }
        return gson.fromJson(jsonCookbook, Cookbook.class);
    }

    private void setupDefaultCookbook() throws Exception {
        //new empty cookbook
        mockMvc.perform(MockMvcRequestBuilders.post("/api/cookbook/new")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();
        
        //add taco
        mockMvc.perform(MockMvcRequestBuilders.post("/api/cookbook")
            .accept(MediaType.APPLICATION_JSON)
            .content(gson.toJson(this.recipe1)))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();

        //add pizza
        mockMvc.perform(MockMvcRequestBuilders.post("/api/cookbook")
            .accept(MediaType.APPLICATION_JSON)
            .content(gson.toJson(this.recipe2)))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();
    }
}
