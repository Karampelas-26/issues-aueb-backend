package com.aueb.issues;

import com.aueb.issues.user.Role;
import com.aueb.issues.user.User;
import com.aueb.issues.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author George Karampelasd
 */
@Component
@Order(1)
@RequiredArgsConstructor
public class initThingsOnStartUp implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {

        User admin = User.builder()
                .email("george")
                .password(passwordEncoder.encode("gk"))
                .phone("6945227237")
                .firstname("george")
                .lastname("karampelas")
                .gender('M')
                .address("Eygeniou Karavias 2, 11144 Attica")
                .createdDate(new Date())
                .role(Role.ADMIN)
                .build();
        userRepository.save(admin);
    }


}
