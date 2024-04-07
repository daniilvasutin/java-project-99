package hexlet.code.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;

@Component
public class UserUtils {
    @Autowired
    private UserRepository userRepository;

    public static final  String ADMIN_EMAIL = "hexlet@example.com";
    public static final  String ADMIN_PASSWORD = "qwerty";


    public User getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        var email = authentication.getName();
        return userRepository.findByEmail(email).get();
    }

    public User createAdmin() {
        var admin = new User();
        admin.setEmail(ADMIN_EMAIL);
        admin.setPassword(ADMIN_PASSWORD);
        return admin;
    }
}
