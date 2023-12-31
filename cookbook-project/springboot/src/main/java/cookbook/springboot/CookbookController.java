package cookbook.springboot;

import cookbook.core.Cookbook;
import cookbook.core.Recipe;
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
    this.cookbookService = service;
  }

  /**
   * Creates a new, empty cookbook.
   *
   * @return ResponseEntity containing status code.
   */
  @PostMapping("/cookbook/new")
  public ResponseEntity<Void> newCookbook() {
    cookbookService.updateCookbook(new Cookbook());
    return new ResponseEntity<Void>(HttpStatus.OK);
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
   * Searches for recipes by name.
   *
   * @param name the name or part of the name to search for.
   * @return a cookbook containing the matching recipes.
   */
  @GetMapping("/cookbook/search/{name}")
  public ResponseEntity<Cookbook> searchRecipes(@PathVariable("name") String name) {
    return new ResponseEntity<Cookbook>(cookbookService
        .getRecipe(name, cookbookService.readCookbook()), HttpStatus.OK);
  }

  /**
   * Filters recipes by their origin country.
   *
   * @param origin the country of origin to filter by.
   * @return a cookbook containing the matching recipes.
   */
  @GetMapping("/cookbook/origin/{origin}")
  public ResponseEntity<Cookbook> filterByOrigin(@PathVariable("origin") String origin) {
    return new ResponseEntity<Cookbook>(cookbookService
        .filterByOrigin(origin, cookbookService.readCookbook()), HttpStatus.OK);
  }

  /**
   * Filters recipes by their type.
   *
   * @param type the type to filter by.
   * @return a cookbook containing the matching recipes.
   */
  @GetMapping("/cookbook/type/{type}")
  public ResponseEntity<Cookbook> filterByType(@PathVariable("type") String type) {
    return new ResponseEntity<Cookbook>(cookbookService
        .filterByType(type, cookbookService.readCookbook()), HttpStatus.OK);
  }

  /**
   * Filters recipes that are marked as favorite.
   *
   * @return a cookbook containing the favorite recipes.
   */
  @GetMapping("/cookbook/favorites")
  public ResponseEntity<Cookbook> filterByFavorite() {
    return new ResponseEntity<Cookbook>(cookbookService
        .filterByFavorite(cookbookService.readCookbook()), HttpStatus.OK);
  }

  /**
   * Filters recipes based on user preferences.
   *
   * @param vgl a string representing user preferences.
   * @return a cookbook containing the matching recipes.
   */
  @GetMapping("/cookbook/preferences/{vgl}")
  public ResponseEntity<Cookbook> filterByPreferences(@PathVariable("vgl") String vgl) {
    return new ResponseEntity<Cookbook>(cookbookService
        .filterByPreferences(vgl, cookbookService.readCookbook()), HttpStatus.OK);
  }

  /**
   * Adds a new recipe to the cookbook.
   *
   * @param recipeJson the JSON representation of the recipe to add.
   * @return ResponseEntity containing the added recipe's JSON.
   */
  @PostMapping("/cookbook")
  public ResponseEntity<String> addRecipe(@RequestBody String recipeJson) {
    if (cookbookService.addRecipe(recipeJson, cookbookService.readCookbook())) {
      return new ResponseEntity<String>(recipeJson, HttpStatus.OK);
    } else {
      return new ResponseEntity<String>(recipeJson, HttpStatus.BAD_REQUEST);
    }
  }

  /**
   * Removes a recipe from the cookbook.
   *
   * @param name the name of the recipe to remove.
   * @return ResponseEntity containing status code.
   */
  @DeleteMapping("/cookbook/recipe/{name}")
  public ResponseEntity<Void> deleteRecipe(@PathVariable("name") String name) {
    return cookbookService.deleteRecipe(name, cookbookService.readCookbook());
  }

  /**
   * Toggles the favorite status of a recipe.
   *
   * @param name
   * @return ResponseEntity containing ok status code.
   */
  @PutMapping("/cookbook/favorite/{name}")
  public ResponseEntity<Recipe> toggleFavorite(@PathVariable("name") String name) {
    Recipe recipe = cookbookService.toggleFavorite(name, cookbookService.readCookbook());
    if (recipe == null) {
      return new ResponseEntity<Recipe>(recipe, HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<Recipe>(recipe, HttpStatus.OK);
  }

  /**
 * Updates a recipe in the cookbook based on the name.
 *
 * @param name name of the recipe.
 * @param updatedRecipeJson recipe in json format that contains the updates
 * @return ResponseEntity containing the updated recipe's JSON.
 */
  @PutMapping("/cookbook/recipe/{name}")
  public ResponseEntity<Recipe> updateRecipe(@PathVariable("name") String name, 
      @RequestBody String updatedRecipeJson) {
    Recipe recipe = cookbookService.updateRecipe(name, updatedRecipeJson, 
        cookbookService.readCookbook());
    if (recipe == null) {
      return new ResponseEntity<Recipe>(recipe, HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<Recipe>(recipe, HttpStatus.OK);
  }   
}
