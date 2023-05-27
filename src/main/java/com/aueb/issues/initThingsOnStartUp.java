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
import java.util.UUID;

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
                .id(String.valueOf(UUID.randomUUID()))
                .email("george.karampelas.26@gmail.com")
                .password(passwordEncoder.encode("pass"))
                .phone("6945227237")
                .firstname("George")
                .lastname("Karampelas")
                .gender('M')
                .address("Eygeniou Karavias 12, 11144 Attica")
                .createdDate(new Date())
                .role(Role.ADMIN)
                .activated(false)
                .build();
        userRepository.save(admin);
        UserEntity teacher = UserEntity.builder()
                .id(String.valueOf(UUID.randomUUID()))
                .email("giorgosmeid@gmail.com")
                .password(passwordEncoder.encode("pass"))
                .phone("6945227238")
                .firstname("Steve")
                .lastname("iDanys")
                .gender('M')
                .address("Eygeniou Karavias 2, 11144 Attica")
                .createdDate(new Date())
                .role(Role.TEACHER)
                .activated(true)
                .build();
        userRepository.save(teacher);
        UserEntity tech = UserEntity.builder()
                .id(String.valueOf(UUID.randomUUID()))
                .email("haralabos13aggelis@hotmail.com")
                .password(passwordEncoder.encode("pass"))
                .phone("6945227238")
                .firstname("Charalampos")
                .lastname("Aggelis")
                .gender('M')
                .address("Eygeniou Karavias 32, 11144 Attica")
                .createdDate(new Date())
                .role(Role.TECHNICIAN)
                .build();
        userRepository.save(tech);
    }


}
