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
  private URI uri = null;

  /**
   * Connects to the springboot-server. 
   * Updates the uri field to http://localhost:8080/api
   */
  private void connect() {
    try {
      if (uri == null) {
        this.uri = new URI("http://localhost:8080/api/");
      }
    } catch (Exception e) {
      System.out.println("URI not found");
      this.uri = null;
    }
  }

  /**
   * Fetches the entire cookbook.
   *
   * @return the cookbook, or null if the file is not found.
   * @throws RuntimeException if the cookbook could not be fetched.
   */
  @Override
  public Cookbook fetchCookbook() throws RuntimeException {
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
      if (response.statusCode() == 200) {
        return ut;
      } else {
        throw new RuntimeException("Failed to fetch remote cookbook");
      }
    } catch (InterruptedException | IOException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Failed to fetch remote cookbook");
    }
  }

  /**
   * Searches for recipes by name.
   *
   * @param recipeName the name or part of the name to search for.
   * @return a cookbook containing the matching recipes.
   * @throws RuntimeException if the cookbook could not be fetched.
   */
  @Override
  public Cookbook searchRecipe(String recipeName) throws RuntimeException {
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
      if (response.statusCode() == 200) {
        return ut;
      } else {
        throw new RuntimeException("Failed to search for recipe");
      }
    } catch (InterruptedException | IOException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Failed to search for recipe");
    }
  }

  /**
   * Filters recipes by their origin country.
   *
   * @param origin the country of origin to filter by.
   * @return a cookbook containing the matching recipes.
   * @throws RuntimeException if the cookbook could not be fetched.
   */
  @Override
  public Cookbook filterByOrigin(String origin) throws RuntimeException {
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
      if (response.statusCode() == 200) {
        return ut;
      } else {
        throw new RuntimeException("Failed to filter by origin");
      }
    } catch (InterruptedException | IOException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Failed to filter by origin");
    }
  }

  /**
   * Filters recipes by their type.
   *
   * @param type the type to filter by.
   * @return a cookbook containing the matching recipes.
   * @throws RuntimeException if the cookbook could not be fetched.
   */
  @Override
  public Cookbook filterByType(String type) throws RuntimeException {
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
      if (response.statusCode() == 200) {
        return ut;
      } else {
        throw new RuntimeException("Failed to filter by type");
      }
    } catch (InterruptedException | IOException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Failed to filter by type");
    }
  }

  /**
   * Filters recipes that are marked as favorite.
   *
   * @return a cookbook containing the favorite recipes.
   * @throws RuntimeException if the cookbook could not be fetched.
   */
  @Override
  public Cookbook filterByFavorite() throws RuntimeException {
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
      if (response.statusCode() == 200) {
        return ut;
      } else {
        throw new RuntimeException("Failed to filter by favorite");
      }
    } catch (InterruptedException | IOException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Failed to filter by favorite");
    }
  }

  /**
   * Filters recipes based on user preferences.
   *
   * @param vlg a string representing user preferences.
   * @return a cookbook containing the matching recipes.
   * @throws RuntimeException if the request fails.
   */
  @Override
  public Cookbook filterByPreferences(String vlg) throws RuntimeException {
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
      if (response.statusCode() == 200) {
        return ut;
      } else {
        throw new RuntimeException("Failed to filter by preferences");
      }
    } catch (InterruptedException | IOException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Failed to filter by preferences");
    }
  }

  /**
   * Updates a recipe in the cookbook.
   *
   * @param recipe the recipe to add.
   * @throws RuntimeException if the recipe could not be updated.
   */
  @Override
  public void updateRecipe(Recipe recipe) throws RuntimeException { //throws runtimeExceiption?
    connect();
    try {
      String encodedName;
      encodedName = recipe.getName().replace(" ", "%20");
      String json = gson.toJson(recipe);
      HttpRequest request = HttpRequest.newBuilder(uri.resolve("cookbook/recipe/" + encodedName))
          .header("Accept", "application/json")
          .header("Content-Type", "application/json")
          .PUT(HttpRequest.BodyPublishers.ofString(json))
          .build();
        
      HttpResponse<String> response = HttpClient.newBuilder()
          .build()
          .send(request, HttpResponse.BodyHandlers.ofString());

      if (response.statusCode() != 200) {
        throw new RuntimeException("Failed to update recipe");
      }
    } catch (InterruptedException | IOException e) {
      throw new RuntimeException("Failed to update recipe");
    }
  }

  /**
   * Removes a recipe from the cookbook.
   *
   * @param recipeName the name of the recipe to remove.
   * @return true if the recipe was removed, false otherwise.
   * @throws RuntimeException if the recipe could not be removed.
   */
  @Override
  public boolean removeRecipe(String recipeName) throws RuntimeException {
    connect();
    try {
      String encodedName;
      encodedName = recipeName.replace(" ", "%20");

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
      throw new RuntimeException();
    }
  }

  /**
   * Adds a recipe to the cookbook.
   *
   * @param recipe the recipe to add. 
   * @throws RuntimeException if the recipe could not be added.
   * @throws IllegalArgumentException if the recipe name is empty or in cookbook.
   */
  @Override
  public void addRecipe(Recipe recipe) throws RuntimeException, IllegalArgumentException {
    connect();
    if (recipe.getName().strip().isEmpty()) {
      throw new IllegalArgumentException("Recipe name cannot be empty");
    }
    try {
      HttpRequest request = HttpRequest.newBuilder(uri.resolve("cookbook"))
          .header("Accept", "application/json")
          .header("Content-Type", "application/json")
          .POST(BodyPublishers.ofString(gson.toJson(recipe)))
          .build();
      
      HttpResponse<String> response = HttpClient.newBuilder()
          .build()
          .send(request, HttpResponse.BodyHandlers.ofString());
        
      if (response.statusCode() != 200) {
        throw new IllegalArgumentException("Could not add recipe");
      }
    } catch (InterruptedException | IOException e) {
      throw new RuntimeException("Error adding recipe");
    }
  }

  /**
   * Adds a recipe to the favorites.
   *
   * @param recipe the name of the recipe to toggle
   * @throws RuntimeException if the recipe could not be toggled.
   */
  public void toggleFavorite(Recipe recipe) throws RuntimeException {
    connect();
    try {
      String encodedName;
      encodedName = recipe.getName().replace(" ", "%20");
      String state = !recipe.getFavorite() + "";
      HttpRequest request = HttpRequest.newBuilder(uri.resolve("cookbook/favorite/" + encodedName))
          .header("Accept", "application/json")
          .header("Content-Type", "application/json")
          .PUT(HttpRequest.BodyPublishers.ofString(state))
          .build();
      
      HttpResponse<String> response = HttpClient.newBuilder()
          .build()
          .send(request, HttpResponse.BodyHandlers.ofString());

      if (response.statusCode() != 200) {
        throw new RuntimeException("Error favoriting recipe");
      }
    } catch (InterruptedException | IOException e) {
      throw new RuntimeException("Error favoriting recipe");
    }
  }

  /**
   * Sets the URI of the server.
   *
   * @param uri the URI of the server.
   */ 
  public void setUri(URI uri) {
    this.uri = uri;
  }
}
