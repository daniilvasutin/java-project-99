package hexlet.code.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

//    @Autowired
//    private UserRepository userRepository;

    @GetMapping(path = "/welcome")
    public String index() {
//        System.out.println("Current JVM version - " + System.getProperty("java.version"));

//        var admin = userRepository.findByEmail("hexlet@example.com").get();

        return "Welcome to Spring!";
    }
}
