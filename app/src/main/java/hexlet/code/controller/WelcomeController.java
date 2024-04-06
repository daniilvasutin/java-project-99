package hexlet.code.controller;

import hexlet.code.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(path = "/welcome")
    public String index() {
        System.out.println("Current JVM version - " + System.getProperty("java.version"));

        var admin = userRepository.findByEmail("hexlet@example.com").get();

        return "Welcome to Spring!";
    }
}
