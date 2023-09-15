package cookbook.json;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

import com.google.gson.Gson;

import cookbook.core.Cookbook;
import cookbook.core.Recipe;

public class CookbookHandler {

    public void writeToFile(Cookbook cookbook) {
      // making gson object
      Gson gson = new Gson();
      // converting 
      String json = gson.toJson(cookbook);
      String filePath = "cookbook.json";

      try (FileWriter writer = new FileWriter(filePath)) {
          writer.write(json);
      } catch (IOException e) {
          e.printStackTrace();
      }
    }

    public Cookbook readFromFile() {
      String filePath = "cookbook.json";

      try (Reader reader = new FileReader(filePath)) {
          // Create a Gson instance
          Gson gson = new Gson();

          // Use Gson to parse the JSON data into an instance of your Java class
          Cookbook cookbook = gson.fromJson(reader, Cookbook.class);

          return cookbook;

      } catch (IOException e) {
          e.printStackTrace();
          System.out.println("File not found");
          return null;
      }
    }



    public static void main(String[] args) {
      CookbookHandler ch = new CookbookHandler();

      System.out.println(ch.readFromFile());
      
    }
}
