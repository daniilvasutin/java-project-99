package hexlet.code.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Encode {

    public static String encode(String password) {
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
        return bc.encode(password);
    }
}
