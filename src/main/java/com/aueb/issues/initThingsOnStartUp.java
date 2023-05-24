package com.aueb.issues;

import com.aueb.issues.model.entity.Role;
import com.aueb.issues.model.entity.UserEntity;
import com.aueb.issues.repository.UserRepository;
import org.springframework.context.annotation.Configuration;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author George Karampelasd
 */

@Configuration
@Component
@Order(1)
@RequiredArgsConstructor
public class initThingsOnStartUp implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {

        UserEntity admin = UserEntity.builder()
                .email("admin")
                .password(passwordEncoder.encode("pass"))
                .phone("6945227237")
                .firstname("george")
                .lastname("karampelas")
                .gender('M')
                .address("Eygeniou Karavias 2, 11144 Attica")
                .createdDate(new Date())
                .role(Role.ADMIN)
                .build();
        userRepository.save(admin);
        UserEntity teacher = UserEntity.builder()
                .email("teach")
                .password(passwordEncoder.encode("pass"))
                .phone("6945227238")
                .firstname("george")
                .lastname("karampelas")
                .gender('M')
                .address("Eygeniou Karavias 2, 11144 Attica")
                .createdDate(new Date())
                .role(Role.TEACHER)
                .build();
        userRepository.save(teacher);
        UserEntity tech = UserEntity.builder()
                .email("tech")
                .password(passwordEncoder.encode("pass"))
                .phone("6945227238")
                .firstname("george")
                .lastname("karampelas")
                .gender('M')
                .address("Eygeniou Karavias 2, 11144 Attica")
                .createdDate(new Date())
                .role(Role.TECHNICIAN)
                .build();
        userRepository.save(tech);
    }


}
