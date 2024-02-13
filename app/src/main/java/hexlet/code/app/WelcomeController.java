package hexlet.code.app;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @GetMapping(path = "/welcome")
    public String index() {
        System.out.println("Current JVM version - " + System.getProperty("java.version"));
        return "Welcome to Spring";
    }
}
