package com.aueb.issues.web.comittee;

import com.aueb.issues.email.EmailService;
import com.aueb.issues.model.entity.ActivationToken;
import com.aueb.issues.model.entity.Role;
import com.aueb.issues.model.entity.UserEntity;
import com.aueb.issues.repository.ActivationTokenRepository;
import com.aueb.issues.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author George Karampelas
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CommitteeService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final ActivationTokenRepository activationTokenRepository;

    public ResponseEntity<CreateUserResponse> createUser(CreateUserRequest request) {

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
