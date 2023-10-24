package cookbook.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CookbookModelApplication {
    
    public static void main(String[] args) {
        System.out.println("App running");
        SpringApplication.run(CookbookModelApplication.class, args);
    }
}
