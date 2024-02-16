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

    @Autowired
    private Environment environment;

//    @Value("${spring.profiles}")
//    private String activeProfile;

    @GetMapping(path = "/welcome")
    public String index() {
        System.out.println("Current JVM version - " + System.getProperty("java.version"));

        User user = new User("hello", "hello@email.ru");
        userRepository.save(user);
        var users = userRepository.findAll();
//        return users.stream()
//                .map(p -> userMapper.map(p))
//                .toList();
        String str = "";
        for (final String profileName : environment.getActiveProfiles()) {
            str += profileName;
            System.out.println("!!! " + "Currently active profile - " + profileName + " !!!");
        }

//        if (System.getenv().get("APP_ENV").equals("production")) {
//
//        }
        return "Welcome to Spring" + " " + users.get(0).getEmail() + "profile: " + str;
    }
}
