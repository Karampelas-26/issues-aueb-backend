package com.aueb.issues.web.service;

import com.aueb.issues.email.EmailService;
import com.aueb.issues.model.entity.ActivationToken;
import com.aueb.issues.model.entity.ApplicationEntity;
import com.aueb.issues.model.entity.UserEntity;
import com.aueb.issues.model.enums.Role;
import com.aueb.issues.repository.ActivationTokenRepository;
import com.aueb.issues.repository.UserRepository;
import com.aueb.issues.web.comittee.CreateUserDTO;
import com.aueb.issues.web.comittee.CreateUserResponse;
import com.aueb.issues.web.dto.TeacherApplicationsDTO;
import com.aueb.issues.web.dto.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    EmailService emailService;
    @Autowired
    ActivationTokenRepository activationTokenRepository;

    public ResponseEntity<CreateUserResponse> createUser(CreateUserDTO request) {
        log.info("i m in");
        UserEntity user = UserEntity.builder()
//                .id(String.valueOf(UUID.randomUUID()))
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
//                .userId(user.getId())
                .activationToken(token)
                .tokenCreationDateTime(LocalDateTime.now())
                .build();

        activationTokenRepository.save(activationToken);

        String activationLink = "http://localhost:4200/activate?token=" + token;
        emailService.sendEmail(user.getEmail(), "Account Activation", activationLink);
        return ResponseEntity.ok(new CreateUserResponse());
    }

    public ResponseEntity<String> deleteUser(Long id) {
        try{
            userRepository.findById(id).orElseThrow(()->new EntityNotFoundException("No such entity found"));
            userRepository.deleteById(id);
            return ResponseEntity.ok(null);
        }catch (Exception e){
            log.error(e.toString());
            return null;
        }
    }

    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> ret = new ArrayList<>();
        try{
            List<UserEntity> users = userRepository.findAll();
            ObjectMapper mapper = new ObjectMapper();
            for (UserEntity user: users){
                mapper.convertValue(user,TeacherApplicationsDTO.class);
            }
        }catch (Exception e){
            log.error(e.toString());
            return null;
        }
        return (ResponseEntity<List<UserDTO>>) ret;
    }

    public ResponseEntity<String> updateUser(Long id, UserDTO request) {
        try{

            UserEntity user = userRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Application not found"));
            if(user!=null) {
                return ResponseEntity.badRequest().body("No user with such id");
            }
            if(request.getPassword()!=null)
                user.setPassword(request.getPassword());
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
            /*if(request.isActivated())
                user.setGender(request.getGender());
            */
            return ResponseEntity.ok(null);

        }catch (Exception e){
            log.error(e.toString());
            return null;
        }
    }
}
