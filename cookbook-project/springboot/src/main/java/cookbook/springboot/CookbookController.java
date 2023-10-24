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
import cookbook.core.Recipe;

@RestController
@RequestMapping("/api")
public class CookbookController {
   
    private CookbookService cookbookService;

    @Autowired
    public CookbookController(CookbookService service) {
        System.out.println("CookbookController constructor");
        this.cookbookService = service;
    }


    @GetMapping("/cookbook")
    public ResponseEntity<Cookbook> getCookbook() {
        Cookbook cookbook = cookbookService.readCookbook();
        return new ResponseEntity<Cookbook>(cookbook, HttpStatus.OK);
    }

    @PutMapping("/cookbook")
    public void updateCookbook(@RequestBody Cookbook updatedCookbook) {
        cookbookService.updateCookbook(updatedCookbook);
    }

    @GetMapping("/cookbook/recipe/{name}")
    public Recipe getRecipe(@PathVariable("name") String name) {
        return cookbookService.getRecipe(name, cookbookService.readCookbook());
    }

    @PostMapping("/cookbook")
    public ResponseEntity<String> addRecipe(@RequestBody String recipeJson) {
        cookbookService.addRecipe(recipeJson, cookbookService.readCookbook());
        return new ResponseEntity<String>(recipeJson, HttpStatus.OK);
    }

    @DeleteMapping("/cookbook/recipe/{name}")
    public void deleteRecipe(@PathVariable("name") String name) {
        System.out.println("Running delteRecipe in CookbookController");
        cookbookService.deleteRecipe(name, cookbookService.readCookbook());
    }

}
