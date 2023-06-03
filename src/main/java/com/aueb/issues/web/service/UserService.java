package com.aueb.issues.web.service;

import com.aueb.issues.email.EmailService;
import com.aueb.issues.model.entity.ActivationToken;
import com.aueb.issues.model.entity.UserEntity;
import com.aueb.issues.model.enums.Role;
import com.aueb.issues.repository.ActivationTokenRepository;
import com.aueb.issues.repository.UserRepository;
import com.aueb.issues.web.comittee.CreateUserDTO;
import com.aueb.issues.web.dto.ResponseMessageDTO;
import com.aueb.issues.web.dto.UserDTO;
import com.aueb.issues.web.dto.UserMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    EmailService emailService;
    @Autowired
    ActivationTokenRepository activationTokenRepository;

    public ResponseEntity<ResponseMessageDTO> createUser(CreateUserDTO request) {
        try {

            Optional<UserEntity> userTemp = userRepository.findByEmail(request.getEmail());
            if(userTemp.isPresent()) return ResponseEntity.badRequest().body(new ResponseMessageDTO("User already exist"));

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
            return ResponseEntity.ok(new ResponseMessageDTO("User send activation token"));
        } catch (IllegalArgumentException e) {
            log.error(e.toString());
            return ResponseEntity.badRequest().body(new ResponseMessageDTO("Bad request for create user"));
        }
    }

    public ResponseEntity<ResponseMessageDTO> deleteUser(Long id) {
        try{
            userRepository.findById(id).orElseThrow(()->new EntityNotFoundException("User not found to delete"));
            userRepository.deleteById(id);
            return ResponseEntity.ok(new ResponseMessageDTO("Users deleted successfully with ID: " + id));
        }catch (Exception e){
            log.error(e.toString());
            return ResponseEntity.badRequest().body(new ResponseMessageDTO(e.getMessage()));
        }
    }

    public ResponseEntity<List<UserDTO>> getUsers() {
        try{
            List<UserEntity> users = userRepository.findAll();

            List<UserDTO> userDTOS = users.stream().map(UserMapper.INSTANCE::toDto).collect(Collectors.toList());
            return ResponseEntity.ok(userDTOS);
        }catch (Exception e){
            log.error(e.toString());
            return ResponseEntity.internalServerError().body(null);
        }
    }

    public ResponseEntity<ResponseMessageDTO> updateUser(Long id, UserDTO request) {
        try{

            UserEntity user = userRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("User not found"));
            if(request.getEmail()!=null)
                user.setEmail(request.getEmail());
            if(request.getPhone()!=null)
                user.setPhone(request.getPhone());
            if(request.getFirstname()!=null)
                user.setFirstname(request.getFirstname());
            if(request.getLastname()!=null)
                user.setLastname(request.getLastname());
            if(request.getGender()!=null)
                user.setGender(request.getGender());
            if(request.getAddress()!=null)
                user.setAddress(request.getAddress());
            if(request.getRole()!=null)
                user.setRole(request.getRole());
            return ResponseEntity.ok(null);

        }catch (Exception e){
            log.error(e.toString());
            return ResponseEntity.badRequest().body(new ResponseMessageDTO(e.getMessage()));
        }
    }
}
