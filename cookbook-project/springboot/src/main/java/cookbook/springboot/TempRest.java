package cookbook.springboot;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;

import com.google.gson.Gson;

import cookbook.core.Cookbook;
import cookbook.core.Recipe;

public class TempRest {
  public static void main(String[] args) {
    Recipe recipe = new Recipe();

    recipe.setName("Pancakes");
    recipe.setDescription("Mix ingredients. Cook on griddle.");
    recipe.setOriginCountry("Norway");
    recipe.addIngredient("egg", "2");
    recipe.addIngredient("flour", "1 cup");
    recipe.addIngredient("milk", "1 cup");
    
    Cookbook cookbook = new Cookbook();
    cookbook.addRecipe(recipe);

    URI uri;
    try {
      uri = new URI("http://localhost:8080/api/");
    } catch (Exception e) {
      System.out.println("URI not found");
      uri = null;
    }
    Gson gson = new Gson();


    // GET
    String responseBody = null;
    try {
      HttpRequest request = HttpRequest.newBuilder(uri.resolve("cookbook"))
        .header("Accept", "application/json")
        .GET()
        .build();
    
      HttpResponse<String> response = HttpClient.newBuilder()
          .build()
          .send(request, HttpResponse.BodyHandlers.ofString());

      responseBody = response.body();
      System.out.println("GET Body:" + responseBody);
    } catch (Exception e) {
      System.out.println("Error sending request");
    }

    // PUT
    try {
      HttpRequest request = HttpRequest.newBuilder(uri.resolve("cookbook"))
        .header("Accept", "application/json")
        .header("Content-Type", "application/json")
        .PUT(BodyPublishers.ofString(gson.toJson(cookbook)))
        .build();
      /* 
      HttpResponse<String> response = HttpClient.newBuilder()
          .build()
          .send(request, HttpResponse.BodyHandlers.ofString());
      
      System.out.println("PUT Body:" + response.body());
      */
    } catch (Exception e) {
      System.out.println("Error sending request");
    }

    // POST
    try {
      HttpRequest request = HttpRequest.newBuilder(uri.resolve("cookbook"))
        .header("Accept", "application/json")
        .header("Content-Type", "application/json")
        .POST(BodyPublishers.ofString(gson.toJson(recipe)))
        .build();
      
      HttpResponse<String> response = HttpClient.newBuilder()
          .build()
          .send(request, HttpResponse.BodyHandlers.ofString());

      System.out.println("POST body" + response.body());
    } catch (Exception e) {
      System.out.println("Error sending request");
    }

    // POST
    try {
      HttpRequest request = HttpRequest.newBuilder(uri.resolve("cookbook"))
        .header("Accept", "application/json")
        .header("Content-Type", "application/json")
        .POST(BodyPublishers.ofString(gson.toJson(recipe)))
        .build();
      /* 
      HttpResponse<String> response = HttpClient.newBuilder()
          .build()
          .send(request, HttpResponse.BodyHandlers.ofString());

      System.out.println("POST body" + response.body());
      */
    } catch (Exception e) {
      System.out.println("Error sending request");
    }

    // DELETE
    try {
      String recipeName = "Spaghetti Bolognese";
      String encodedName;
      try {
        encodedName = URLEncoder.encode(recipeName, "UTF-8");
      } catch (UnsupportedEncodingException e) {
        encodedName = recipeName;
        e.printStackTrace();
      }

      System.out.println(recipeName);
      HttpRequest request = HttpRequest.newBuilder(uri.resolve("cookbook/recipe/" + encodedName))
        .header("Accept", "application/json")
        .header("Content-Type", "application/json")
        .DELETE()
        .build();
      
      HttpResponse<String> response = HttpClient.newBuilder()
          .build()
          .send(request, HttpResponse.BodyHandlers.ofString());

      System.out.println("DELETE body" + response.body());
    } catch (Exception e) {
      System.out.println("Error sending request");
    }

  }
}
