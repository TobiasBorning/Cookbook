package cookbook.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cookbook.core.Cookbook;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

/**
 * The {@code CookbookHandler} class is responsible for handling the persistence 
 * of {@code Cookbook} objects. It provides methods to write a {@code Cookbook} to a 
 * file and read a {@code Cookbook} from a file using JSON format.
 */
public class CookbookHandler {

  /**
  * Writes cookbook to file using Gson.
  *
  * @param cookbook the cookbook to be written
  * @param path path of json file
  *    
  * @throws FileNotFoundException throws exception if the path is not found
  */
  public void writeToFile(Cookbook cookbook, String path) throws FileNotFoundException {
    // making gson object
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    // converting 
    String json = gson.toJson(cookbook);
    String filePath = path;

    try (FileWriter writer = new FileWriter(filePath)) {
      writer.write(json);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
  * Reads cookbook from file via filepath using Gson.
  *
  * @param path the file path where the cookbook JSON data is stored
  * @return cookbook from json file
  * @throws FileNotFoundException throws exception
  */
  public Cookbook readFromFile(String path) throws FileNotFoundException {
    String filePath = path;

    try (Reader reader = new FileReader(filePath)) {
      // Create a Gson instance
      Gson gson = new Gson();

      // Use Gson to parse the JSON data into an instance of your Java class
      Cookbook cookbook = gson.fromJson(reader, Cookbook.class);

      return cookbook;

    } catch (IOException e) {
      e.printStackTrace();
      throw new FileNotFoundException("File not found");
    }
  }     
}
