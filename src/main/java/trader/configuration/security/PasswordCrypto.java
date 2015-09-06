package trader.configuration.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordCrypto {

    //@Autowired
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private static PasswordCrypto instance;

    public static PasswordCrypto getInstance() {
        if(instance == null) {
            instance = new PasswordCrypto();
        }

        return instance;
    }

    public String encrypt(String str) {
        return passwordEncoder.encode(str);
    }
    
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        if(passwordEncoder == null) {
//        	passwordEncoder = new BCryptPasswordEncoder();
//        }
//
//        return passwordEncoder;
//    }
}
