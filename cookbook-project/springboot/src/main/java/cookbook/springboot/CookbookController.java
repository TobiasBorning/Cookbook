package cookbook.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cookbook.core.Cookbook;

/**
 * REST Controller for managing cookbook operations.
 */
@RestController
@RequestMapping("/api")
public class CookbookController {
   
    private CookbookService cookbookService;

    /**
     * Constructs a new CookbookController with the specified CookbookService.
     * 
     * @param service the service to be used by this controller.
     */
    @Autowired
    public CookbookController(CookbookService service) {
        System.out.println("CookbookController constructor");
        this.cookbookService = service;
    }

    /**
     * Retrieves the entire cookbook.
     * 
     * @return ResponseEntity containing the cookbook.
     */
    @GetMapping("/cookbook")
    public ResponseEntity<Cookbook> getCookbook() {
        Cookbook cookbook = cookbookService.readCookbook();
        return new ResponseEntity<Cookbook>(cookbook, HttpStatus.OK);
    }

    /**
     * Updates the cookbook with the provided data.
     * 
     * @param updatedCookbook the updated cookbook data.
     */
    @PutMapping("/cookbook")
    public void updateCookbook(@RequestBody Cookbook updatedCookbook) {
        cookbookService.updateCookbook(updatedCookbook);
    }

    /**
     * Searches for recipes by name.
     * 
     * @param name the name or part of the name to search for.
     * @return a cookbook containing the matching recipes.
     */
    @GetMapping("/cookbook/search/{name}")
    public Cookbook searchRecipes(@PathVariable("name") String name) {
        return cookbookService.getRecipe(name, cookbookService.readCookbook());
    }

    /**
     * Filters recipes by their origin country.
     * 
     * @param origin the country of origin to filter by.
     * @return a cookbook containing the matching recipes.
     */
    @GetMapping("/cookbook/origin/{origin}")
    public Cookbook filterByOrigin(@PathVariable("origin") String origin) {
        return cookbookService.filterByOrigin(origin, cookbookService.readCookbook());
    }

    /**
     * Filters recipes by their type.
     * 
     * @param type the type to filter by.
     * @return a cookbook containing the matching recipes.
     */
    @GetMapping("/cookbook/type/{type}")
    public Cookbook filterByType(@PathVariable("type") String type) {
        return cookbookService.filterByType(type, cookbookService.readCookbook());
    }

    /**
     * Filters recipes that are marked as favorite.
     * 
     * @return a cookbook containing the favorite recipes.
     */
    @GetMapping("/cookbook/favorites")
    public Cookbook filterByFavorite() {
        return cookbookService.filterByFavorite(cookbookService.readCookbook());
    }

    /**
     * Filters recipes based on user preferences.
     * 
     * @param vgl a string representing user preferences.
     * @return a cookbook containing the matching recipes.
     */
    @GetMapping("/cookbook/preferences/{vgl}")
    public Cookbook filterByPreferences(@PathVariable("vgl") String vgl) {
        return cookbookService.filterByPreferences(vgl,cookbookService.readCookbook());
    }

    /**
     * Adds a new recipe to the cookbook.
     * 
     * @param recipeJson the JSON representation of the recipe to add.
     * @return ResponseEntity containing the added recipe's JSON.
     */
    @PostMapping("/cookbook")
    public ResponseEntity<String> addRecipe(@RequestBody String recipeJson) {
        cookbookService.addRecipe(recipeJson, cookbookService.readCookbook());
        return new ResponseEntity<String>(recipeJson, HttpStatus.OK);
    }

    /**
     * Removes a recipe from the cookbook.
     * 
     * @param name the name of the recipe to remove.
     */
    @DeleteMapping("/cookbook/recipe/{name}")
    public void deleteRecipe(@PathVariable("name") String name) {
        System.out.println("Running delteRecipe in CookbookController");
        cookbookService.deleteRecipe(name, cookbookService.readCookbook());
    }
}
