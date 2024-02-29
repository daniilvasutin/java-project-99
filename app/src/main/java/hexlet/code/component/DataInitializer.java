package hexlet.code.component;

import hexlet.code.model.User;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.repository.UserRepository;
import hexlet.code.service.CustomUserDetailsService;
import hexlet.code.util.TaskStatusUtil;
import hexlet.code.util.UserUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataInitializer implements ApplicationRunner {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomUserDetailsService userService;

    @Autowired
    private TaskStatusRepository taskStatusRepository;

    @Autowired
    private TaskStatusUtil taskStatusUtil;

    @Autowired
    private UserUtils userUtils;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        String adminEmail = "hexlet@example.com";
//        String adminFirstName = "admin";
//        String adminLastName = "nimda";
//        String password = "qwerty";
//        System.out.println("!!!PAS!!!" + password);
//
//        User admin = new User();
//        admin.setEmail(adminEmail);
//        admin.setFirstName(adminFirstName);
//        admin.setLastName(adminLastName);
//        admin.setPasswordDigest(passwordEncoder.encode(password));
//        System.out.println("!!!PASENC!!!" + admin.getPassword());
//
//        System.out.println("Password is equal to BCryptPassword" + passwordEncoder.matches("qwerty", admin.getPassword()));
//
//        userRepository.save(admin);

//        var email = "hexlet@example.com";
//        var userData = new User();
//        userData.setEmail(email);
//        userData.setPassword("qwerty");
//        userService.createUser(userData);

        var admin = userUtils.createAdmin();
        userService.createUser(admin);

        var taskStatuses = taskStatusUtil.getDefaultTaskStatus();
        taskStatuses.stream().forEach(taskStatus -> taskStatusRepository.save(taskStatus));



//        String User2Email = "User2hexlet@example.com";
//        String User2FirstName = "User2";
//        String User2LastName = "2resu";
//        String User2password = "qwerty";
//        System.out.println("!!!PAS!!!" + User2password);
//        User User2 = new User();
//        User2.setEmail(User2Email);
//        User2.setFirstName(User2FirstName);
//        User2.setLastName(User2LastName);
//        User2.setPassword(User2password);
//        System.out.println("!!!PASENC!!!" + User2.getPassword());
////        System.out.println(bc.matches("qwerty", admin.getPassword()));
////        System.out.println(bc.matches("qwerty", User2.getPassword()));
//
//        userRepository.save(User2);
    }
}
