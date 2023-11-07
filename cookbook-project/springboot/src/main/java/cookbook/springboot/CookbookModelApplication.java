package cookbook.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hovedklassen for oppstart av CookbookModel-applikasjonen.
 * 
 * <p>Denne klassen inneholder oppstartsmetoden for en Spring Boot-applikasjon.
 */
@SpringBootApplication
public class CookbookModelApplication {
    
  /**
   * Hovedmetoden som brukes til å starte Spring Boot-applikasjonen.
   *
   * @param args kommandolinjeargumenter som kan brukes til å konfigurere applikasjonen.
   */

  public static void main(String[] args) {
    System.out.println("App running");
    SpringApplication.run(CookbookModelApplication.class, args);
  }
}
