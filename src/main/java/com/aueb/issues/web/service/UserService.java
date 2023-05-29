package com.aueb.issues.web.service;

import com.aueb.issues.email.EmailService;
import com.aueb.issues.model.entity.ActivationToken;
import com.aueb.issues.model.entity.UserEntity;
import com.aueb.issues.model.enums.Role;
import com.aueb.issues.repository.ActivationTokenRepository;
import com.aueb.issues.repository.UserRepository;
import com.aueb.issues.web.comittee.CreateUserDTO;
import com.aueb.issues.web.comittee.CreateUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    EmailService emailService;
    @Autowired
    ActivationTokenRepository activationTokenRepository;

    public ResponseEntity<CreateUserResponse> createUser(CreateUserDTO request) {

        UserEntity user = UserEntity.builder()
                .id(String.valueOf(UUID.randomUUID()))
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .phone(request.getPhone())
                .address(request.getAddress())
                .gender(request.getGender())
                .role(Role.valueOf(request.getRole()))
                .activated(false)
                .build();
        userRepository.save(user);

        String token = UUID.randomUUID().toString();

        ActivationToken activationToken = ActivationToken.builder()
                .userId(user.getId())
                .activationToken(token)
                .tokenCreationDateTime(LocalDateTime.now())
                .build();

        activationTokenRepository.save(activationToken);

        String activationLink = "http://localhost:4200/activate?token=" + token;
        emailService.sendEmail(user.getEmail(), "Account Activation", activationLink);
        return ResponseEntity.ok(new CreateUserResponse());
    }
}
