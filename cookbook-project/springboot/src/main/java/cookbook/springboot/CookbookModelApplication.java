package cookbook.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This class contains a main method to start the spring boot server application.
 */
@SpringBootApplication
public class CookbookModelApplication {
    
  /**
   * Main method for staring the spring boot server application..
   *
   * @param args command line arguments
   */
  public static void main(String[] args) {
    SpringApplication.run(CookbookModelApplication.class, args);
  }
}
