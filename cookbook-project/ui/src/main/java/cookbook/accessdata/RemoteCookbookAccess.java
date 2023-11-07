package cookbook.accessdata;

import com.google.gson.Gson;
import cookbook.core.Cookbook;
import cookbook.core.Recipe;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;


/**
 * Provides remote access to a cookbook stored in a springboot-server.
 */
public class RemoteCookbookAccess implements CookbookAccess {
  
  private Gson gson = new Gson();
  private URI uri;

  /**
   * Connects to the springboot-server. 
   * Updates the uri field to http://localhost:8080/api
   */
  private void connect() {
    try {
      this.uri = new URI("http://localhost:8080/api/");
    } catch (Exception e) {
      System.out.println("URI not found");
      this.uri = null;
    }
  }

  /**
   * Fetches the entire cookbook.
   *
   * @return the cookbook, or null if the file is not found.
   */
  @Override
  public Cookbook fetchCookbook() {
    connect();
    try {
      HttpRequest request = HttpRequest.newBuilder(uri.resolve("cookbook"))
          .header("Accept", "application/json")
          .GET()
          .build();
      
      HttpResponse<String> response = HttpClient.newBuilder()
          .build()
          .send(request, HttpResponse.BodyHandlers.ofString());
      Cookbook ut = gson.fromJson(response.body(), Cookbook.class);
      //System.out.println(ut.getRecipes().stream().map(Recipe::getName).toList());
      return ut;
    } catch (InterruptedException | IOException e) {
      System.out.println(e.getMessage());
      return new Cookbook(); //returns empty cookbook in case of error
    }
  }

  /**
   * Searches for recipes by name.
   *
   * @param recipeName the name or part of the name to search for.
   * @return a cookbook containing the matching recipes.
   */
  @Override
  public Cookbook searchRecipe(String recipeName) {
    connect();
    String encodedName;
    encodedName = recipeName.replace(" ", "%20");
    try {
      HttpRequest request = HttpRequest.newBuilder(uri.resolve("cookbook/search/" + encodedName))
          .header("Accept", "application/json")
          .GET()
          .build();
      HttpResponse<String> response = HttpClient.newBuilder()
          .build()
          .send(request, HttpResponse.BodyHandlers.ofString());
      Cookbook ut = gson.fromJson(response.body(), Cookbook.class);
      System.out.println(ut.getRecipes().stream().map(Recipe::getName).toList());
      return ut;
    } catch (InterruptedException | IOException e) {
      System.out.println(e.getMessage());
      return new Cookbook(); //returns empty cookbook in case of error
    }
  }

  /**
   * Filters recipes by their origin country.
   *
   * @param origin the country of origin to filter by.
   * @return a cookbook containing the matching recipes.
   */
  @Override
  public Cookbook filterByOrigin(String origin) {
    connect();
    try {
      HttpRequest request = HttpRequest.newBuilder(uri.resolve("cookbook/origin/" + origin))
          .header("Accept", "application/json")
          .GET()
          .build();
      
      HttpResponse<String> response = HttpClient.newBuilder()
          .build()
          .send(request, HttpResponse.BodyHandlers.ofString());
      Cookbook ut = gson.fromJson(response.body(), Cookbook.class);
      System.out.println(ut.getRecipes().stream().map(Recipe::getName).toList());
      return ut;
    } catch (InterruptedException | IOException e) {
      System.out.println(e.getMessage());
      return new Cookbook(); //returns empty cookbook in case of error
    }
  }

  /**
   * Filters recipes by their type.
   *
   * @param type the type to filter by.
   * @return a cookbook containing the matching recipes.
   */
  @Override
  public Cookbook filterByType(String type) {
    connect();
    try {
      HttpRequest request = HttpRequest.newBuilder(uri.resolve("cookbook/type/" + type))
          .header("Accept", "application/json")
          .GET()
          .build();
      
      HttpResponse<String> response = HttpClient.newBuilder()
          .build()
          .send(request, HttpResponse.BodyHandlers.ofString());
      Cookbook ut = gson.fromJson(response.body(), Cookbook.class);
      System.out.println(ut.getRecipes().stream().map(Recipe::getName).toList());
      return ut;
    } catch (InterruptedException | IOException e) {
      System.out.println(e.getMessage());
      return new Cookbook(); //returns empty cookbook in case of error
    }
  }

  /**
   * Filters recipes that are marked as favorite.
   *
   * @return a cookbook containing the favorite recipes.
   */
  @Override
  public Cookbook filterByFavorite() {
    connect();
    try {
      HttpRequest request = HttpRequest.newBuilder(uri.resolve("cookbook/favorites"))
          .header("Accept", "application/json")
          .GET()
          .build();
      
      HttpResponse<String> response = HttpClient.newBuilder()
          .build()
          .send(request, HttpResponse.BodyHandlers.ofString());
      Cookbook ut = gson.fromJson(response.body(), Cookbook.class);
      System.out.println(ut.getRecipes().stream().map(Recipe::getName).toList());
      return ut;
    } catch (InterruptedException | IOException e) {
      System.out.println(e.getMessage());
      return new Cookbook(); //returns empty cookbook in case of error
    }
  }

  /**
   * Filters recipes based on user preferences.
   *
   * @param vlg a string representing user preferences.
   * @return a cookbook containing the matching recipes.
   */
  @Override
  public Cookbook filterByPreferences(String vlg) {
    // String TTT if user is vegetarian, lactose intolerant and gluten intolerant
    // String TTF if the user is vegetarian and lactose intolerant but not gluten intolerant
    // ... and so on
    connect();
    try {
      HttpRequest request = HttpRequest.newBuilder(uri.resolve("cookbook/preferences/" + vlg))
          .header("Accept", "application/json")
          .GET()
          .build();
      
      HttpResponse<String> response = HttpClient.newBuilder()
          .build()
          .send(request, HttpResponse.BodyHandlers.ofString());
      Cookbook ut = gson.fromJson(response.body(), Cookbook.class);
      System.out.println(ut.getRecipes().stream().map(Recipe::getName).toList());
      return ut;
    } catch (InterruptedException | IOException e) {
      System.out.println(e.getMessage());
      return new Cookbook(); //returns empty cookbook in case of error
    }
  }

  /**
   * Updates a recipe in the cookbook.
   *
   * @param recipe the recipe to add.
   */
  @Override
  public void updateRecipe(Recipe recipe) {
    connect();
    try {
      String encodedName;
      encodedName = recipe.getName().replace(" ", "%20");
      String json = gson.toJson(recipe);
      System.out.println("Recipe:" + json);
      HttpRequest request = HttpRequest.newBuilder(uri.resolve("cookbook/recipe/" + encodedName))
          .header("Accept", "application/json")
          .header("Content-Type", "application/json")
          .PUT(HttpRequest.BodyPublishers.ofString(json))
          .build();
        
      HttpResponse<String> response = HttpClient.newBuilder()
          .build()
          .send(request, HttpResponse.BodyHandlers.ofString());

      if (response.statusCode() == 200) {
        System.out.println("Updated recipe");
      } else {
        System.out.println("Error updating recipe");
      }
    } catch (InterruptedException | IOException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Removes a recipe from the cookbook.
   *
   * @param recipeName the name of the recipe to remove.
   */
  @Override
  public boolean removeRecipe(String recipeName) {
    connect();
    try {
      String encodedName;
      encodedName = recipeName.replace(" ", "%20");

      System.out.println(encodedName);
      HttpRequest request = HttpRequest.newBuilder(uri.resolve("cookbook/recipe/" + encodedName))
          .header("Accept", "application/json")
          .header("Content-Type", "application/json")
          .DELETE()
          .build();
      
      HttpResponse<String> response = HttpClient.newBuilder()
          .build()
          .send(request, HttpResponse.BodyHandlers.ofString());

      if (response.statusCode() == 200) {
        System.out.println("Removed recipe");
        return true;
      } else {
        System.out.println("Error removing recipe");
        return false;
      }
    } catch (InterruptedException | IOException e) {
      System.out.println("Error sending request");
      return false;
    }
  }

  /**
   * Adds a recipe to the cookbook.
   *
   * @param recipe the recipe to add. 
   */
  @Override
  public void addRecipe(Recipe recipe) {
    connect();
    try {
      HttpRequest request = HttpRequest.newBuilder(uri.resolve("cookbook"))
          .header("Accept", "application/json")
          .header("Content-Type", "application/json")
          .POST(BodyPublishers.ofString(gson.toJson(recipe)))
          .build();
      
      HttpResponse<String> response = HttpClient.newBuilder()
          .build()
          .send(request, HttpResponse.BodyHandlers.ofString());
        
      if (response.statusCode() == 200) {
        System.out.println("Added recipe");
      } else {
        System.out.println("Error adding recipe");
      }
    } catch (InterruptedException | IOException e) {
      System.out.println("Error sending request");
    }
  }

  /**
   * Adds a recipe to the favorites.
   *
   * @param recipe the name of the recipe to toggle
   */
  public void toggleFavorite(Recipe recipe) {
    connect();
    try {
      String encodedName;
      encodedName = recipe.getName().replace(" ", "%20");
      String state = !recipe.isFavorite() + "";
      HttpRequest request = HttpRequest.newBuilder(uri.resolve("cookbook/favorite/" + encodedName))
          .header("Accept", "application/json")
          .header("Content-Type", "application/json")
          .PUT(HttpRequest.BodyPublishers.ofString(state))
          .build();
      
      HttpResponse<String> response = HttpClient.newBuilder()
          .build()
          .send(request, HttpResponse.BodyHandlers.ofString());

      if (response.statusCode() == 200) {
        System.out.println(recipe.getName() + " is favorite: " + state);
      } else {
        System.out.println("Error updating recipe");
      }
    } catch (InterruptedException | IOException e) {
      System.out.println(e.getMessage());
    }
  }
}
