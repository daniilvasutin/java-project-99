package hexlet.code.controller;

import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(path = "/welcome")
    public String index() {
        System.out.println("Current JVM version - " + System.getProperty("java.version"));

        User user = new User("hello", "hello@email.ru");
        userRepository.save(user);
        var users = userRepository.findAll();
//        return users.stream()
//                .map(p -> userMapper.map(p))
//                .toList();

        return "Welcome to Spring" + " " + users.get(0).getEmail();
    }
}