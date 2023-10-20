package cookbook.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("cookbook/recipe/{name}")
    public Recipe getRecipe(@PathVariable String name) {
        for (Recipe recipe : cookbookService.readCookbook().getRecipes()) {
            if (recipe.getName().equals(name)) {
                return recipe;
            }
        }
        return null;
    }

}
