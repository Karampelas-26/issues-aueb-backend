package com.aueb.issues;

import com.aueb.issues.model.entity.*;
import com.aueb.issues.repository.BuildingRepository;
import com.aueb.issues.repository.SitesRepository;
import com.aueb.issues.repository.TechnicianRepository;
import com.aueb.issues.repository.UserRepository;
import org.springframework.context.annotation.Configuration;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    @Autowired
    private TechnicianRepository technicianRepository;
    @Autowired
    private BuildingRepository buildingRepository;
    @Autowired
    private SitesRepository sitesRepository;
    LocalDateTime date = LocalDateTime.now();
    @Override
    public void run(String... args) throws Exception {
        BuildingEntity marsaleio = BuildingEntity.builder()
                .id(0)
                .name("marsaleio megaro")
                .address("28hs oktombriou")
                .floors(5)
                .build();
        buildingRepository.save(marsaleio);
        SitesEntity a32 =  SitesEntity.builder()
                .id(String.valueOf(UUID.randomUUID()))
                .name("A 32")
                .buildingId(String.valueOf(marsaleio.getId()))
                .floor("3")
                .build();
        sitesRepository.save(a32);
        ApplicationEntity issue1 = ApplicationEntity.builder()
                .id(String.valueOf(UUID.randomUUID()))
                .title("Progector error")
                .sites(a32)
                .building(marsaleio)
                .priority(Priority.MEDIUM)
                .createDate(date)
                .completionDate(date.plusDays(3))
                .build();
        technicianRepository.save(issue1);

        ApplicationEntity issue2 = ApplicationEntity.builder()
                .id(String.valueOf(UUID.randomUUID()))
                .title("HDMI error")
                .sites(a32)
                .building(marsaleio)
                .priority(Priority.LOW)
                .createDate(date)
                .completionDate(date.plusDays(4))
                .build();
        technicianRepository.save(issue2);

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
