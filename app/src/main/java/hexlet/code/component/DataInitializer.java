package hexlet.code.component;

import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataInitializer implements ApplicationRunner {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String adminEmail = "hexlet@example.com";
        String adminFirstName = "admin";
        String adminLastName = "nimda";
        String password = "qwerty";
//        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
        System.out.println("!!!PAS!!!" + password);

        User admin = new User();
        admin.setEmail(adminEmail);
        admin.setFirstName(adminFirstName);
        admin.setLastName(adminLastName);
        admin.setPassword(password);
        System.out.println("!!!PASENC!!!" + admin.getPassword());

        userRepository.save(admin);

        String User2Email = "User2hexlet@example.com";
        String User2FirstName = "User2";
        String User2LastName = "2resu";
        String User2password = "qwerty";
        System.out.println("!!!PAS!!!" + User2password);
        User User2 = new User();
        User2.setEmail(User2Email);
        User2.setFirstName(User2FirstName);
        User2.setLastName(User2LastName);
        User2.setPassword(User2password);
        System.out.println("!!!PASENC!!!" + User2.getPassword());
//        System.out.println(bc.matches("qwerty", admin.getPassword()));
//        System.out.println(bc.matches("qwerty", User2.getPassword()));

        userRepository.save(User2);
    }
}
