package hexlet.code.controller;

import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @Autowired
    private UserRepository userRepository;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @GetMapping(path = "/welcome")
    public String index() {
        System.out.println("Current JVM version - " + System.getProperty("java.version"));

        var admin = userRepository.findByEmail("hexlet@example.com").get();

        System.out.println("Welcome to Spring" + " " + admin.getEmail() + admin.getId() + "create: " + admin.getCreatedAt() + "update: "+ admin.getUpdatedAt() + "profile: " + activeProfile);
        return "Welcome to Spring!";
    }
}
