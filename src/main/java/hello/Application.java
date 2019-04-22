package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        // Launches this class as a web application with the bundled web server. Will
        // default to localhost:8080
        SpringApplication.run(Application.class, args);
    }
}
