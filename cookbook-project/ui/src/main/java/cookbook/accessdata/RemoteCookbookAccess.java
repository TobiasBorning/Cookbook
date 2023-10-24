package cookbook.accessdata;

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

public class RemoteCookbookAccess implements CookbookAccess {
    
    private Gson gson = new Gson();
    private URI uri;
    
    private void connect() {
        try {
            this.uri = new URI("http://localhost:8080/api/");
        } catch (Exception e) {
            System.out.println("URI not found");
            this.uri = null;
        }
    }

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
            System.out.println(ut.getRecipes().stream().map(Recipe::getName).toList());
            return ut;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Cookbook searchRecipe(String recipeName) {
        connect();
        try {
            HttpRequest request = HttpRequest.newBuilder(uri.resolve("cookbook/search/"+recipeName))
                .header("Accept", "application/json")
                .GET()
                .build();
            
            HttpResponse<String> response = HttpClient.newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());
            Cookbook ut = gson.fromJson(response.body(), Cookbook.class);
            System.out.println(ut.getRecipes().stream().map(Recipe::getName).toList());
            return ut;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Cookbook filterByOrigin(String origin) {
        connect();
        try {
            HttpRequest request = HttpRequest.newBuilder(uri.resolve("cookbook/origin/"+origin))
                .header("Accept", "application/json")
                .GET()
                .build();
            
            HttpResponse<String> response = HttpClient.newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());
            Cookbook ut = gson.fromJson(response.body(), Cookbook.class);
            System.out.println(ut.getRecipes().stream().map(Recipe::getName).toList());
            return ut;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Cookbook filterByType(String type) {
        connect();
        try {
            HttpRequest request = HttpRequest.newBuilder(uri.resolve("cookbook/type/"+type))
                .header("Accept", "application/json")
                .GET()
                .build();
            
            HttpResponse<String> response = HttpClient.newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());
            Cookbook ut = gson.fromJson(response.body(), Cookbook.class);
            System.out.println(ut.getRecipes().stream().map(Recipe::getName).toList());
            return ut;
        } catch (Exception e) {
            return null;
        }
    }

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
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Cookbook filterByPreferences(String vlg) {
        connect();
        try {
            HttpRequest request = HttpRequest.newBuilder(uri.resolve("cookbook/preferences/"+vlg))
                .header("Accept", "application/json")
                .GET()
                .build();
            
            HttpResponse<String> response = HttpClient.newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());
            Cookbook ut = gson.fromJson(response.body(), Cookbook.class);
            System.out.println(ut.getRecipes().stream().map(Recipe::getName).toList());
            return ut;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void updateRecipe(Recipe recipe) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateRecipe'");
    }

    @Override
    public void removeRecipe(String recipeName) {
        try {
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
            System.out.println("Removed recipe" + response.body());
        } catch (Exception e) {
            System.out.println("Error sending request");
        }
    }

    @Override
    public void addRecipe(Recipe recipe) {
        try {
            HttpRequest request = HttpRequest.newBuilder(uri.resolve("cookbook"))
              .header("Accept", "application/json")
              .header("Content-Type", "application/json")
              .POST(BodyPublishers.ofString(gson.toJson(recipe)))
              .build();
            
            HttpResponse<String> response = HttpClient.newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());
      
            System.out.println("Added recipe" + response.body());
        } catch (Exception e) {
            System.out.println("Error sending request");
        }
    }

}
