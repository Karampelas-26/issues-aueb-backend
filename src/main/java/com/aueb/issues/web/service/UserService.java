package com.aueb.issues.web.service;

import com.aueb.issues.email.EmailService;
import com.aueb.issues.model.entity.ActivationToken;
import com.aueb.issues.model.entity.UserEntity;
import com.aueb.issues.model.enums.IssueType;
import com.aueb.issues.model.enums.Role;
import com.aueb.issues.repository.ActivationTokenRepository;
import com.aueb.issues.repository.UserRepository;
import com.aueb.issues.repository.representations.UserRepresentation;
import com.aueb.issues.repository.service.CsvParser;
import com.aueb.issues.web.comittee.CreateUserDTO;
import com.aueb.issues.web.dto.ResponseMessageDTO;
import com.aueb.issues.web.dto.UserDTO;
import com.aueb.issues.model.mapper.UserMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    UserRepository userRepository;
    @Autowired
    EmailService emailService;
    @Autowired
    ActivationTokenRepository activationTokenRepository;
    @Autowired
    CsvParser csvParser;
    @Autowired
    PasswordEncoder passwordEncoder;

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
                    .createdDate(LocalDateTime.now())
                    .role(Role.valueOf(request.getRole()))
                    .activated(false)
                    .build();
            userRepository.save(user);

            activationLinkForUser(user);

            return ResponseEntity.ok(new ResponseMessageDTO("User send activation token"));
        } catch (IllegalArgumentException e) {
            log.error(e.toString());
            return ResponseEntity.badRequest().body(new ResponseMessageDTO("Bad request for create user"));
        }
    }

    public ResponseEntity<ResponseMessageDTO> createUserCsv(MultipartFile file){

        try {
            List<UserRepresentation> userCSV =  csvParser.readUserCsv(file);
            for(UserRepresentation usr: userCSV) {
                Optional<UserEntity> usrExists = userRepository.findByEmail(usr.getEmail());
                if(!usrExists.isEmpty()){
                    log.info("User with email: " + usr.getEmail() + " already exists");
                    return ResponseEntity.badRequest().body(new ResponseMessageDTO("User with email: " + usr.getEmail() + " already exists"));
                }
                UserEntity user = UserEntity.builder()
                        .id(UUID.randomUUID().toString())
                        .email(usr.getEmail())
                        .phone(usr.getPhone())
                        .firstname(usr.getFName())
                        .lastname(usr.getLName())
                        .gender(usr.getGender())
                        .address(usr.getAddress())
                        .createdDate(LocalDateTime.now())
                        .role(Role.valueOf(usr.getRole()))
                        .activated(false)
                        .build();
                userRepository.save(user);
                activationLinkForUser(user);
            }

            return ResponseEntity.ok(new ResponseMessageDTO("Users successfully created"));
        } catch (Exception e) {
            log.error(e.toString());
            return ResponseEntity.internalServerError().body(new ResponseMessageDTO(e.getMessage()));

        }
    }


    private void activationLinkForUser(UserEntity user){
        String token = UUID.randomUUID().toString();

        ActivationToken activationToken = ActivationToken.builder()
                .userId(user.getId())
                .activationToken(token)
                .tokenCreationDateTime(LocalDateTime.now())
                .build();

        activationTokenRepository.save(activationToken);

        String activationLink = "http://localhost:4200/activate?token=" + token;
        emailService.sendEmail(user.getEmail(), "Account Activation", activationLink);
    }

    public ResponseEntity<ResponseMessageDTO> deleteUser(String id) {
        try{
            log.info("in here");
            UserEntity user = userRepository.findById(id).orElseThrow(()->new EntityNotFoundException("User not found to delete"));
            user.setActivated(false);
            userRepository.save(user);
            return ResponseEntity.ok(new ResponseMessageDTO("Users deleted successfully with ID: " + id));
        }catch (Exception e){
            log.error(e.toString());
            return ResponseEntity.badRequest().body(new ResponseMessageDTO(e.getMessage()));
        }
    }

    public ResponseEntity<List<UserDTO>> getUsers() {
        try{
            List<UserEntity> users = userRepository.findAll();

            List<UserEntity> filteredUsers = users.stream()
                    .filter(UserEntity::isEnabled)
                    .toList();

            List<UserDTO> userDTOS = filteredUsers.stream().map(UserMapper.INSTANCE::toDto).toList();
            return ResponseEntity.ok(userDTOS);
        }catch (Exception e){
            log.error(e.toString());
            return ResponseEntity.internalServerError().body(null);
        }
    }
    public ResponseEntity<List<UserDTO>> getUserByTechTeam(String issueType){
        if(issueType!=null  && ! (issueType.equals(IssueType.CLIMATE_CONTROL.name())||issueType.equals(IssueType.ELECTRICAL.name())||
                issueType.equals(IssueType.EQUIPMENT.name())||issueType.equals(IssueType.INFRASTRUCTURE.name()))){
            log.error("Incorrect IssueType");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userRepository.findByTechTeam(IssueType.valueOf(issueType)).stream().map(UserMapper.INSTANCE::toDto).toList(),HttpStatus.OK);
    }

    public ResponseEntity<ResponseMessageDTO> updateUser(UserDTO request) {
        try{

            UserEntity user = userRepository.findById(request.getId()).orElseThrow(()-> new EntityNotFoundException("User not found"));
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

            userRepository.save(user);
            return ResponseEntity.ok(new ResponseMessageDTO("User saved successfully"));

        }catch (Exception e){
            log.error(e.toString());
            return ResponseEntity.badRequest().body(new ResponseMessageDTO(e.getMessage()));
        }
    }

    public ResponseEntity<ResponseMessageDTO>setPreferences(UserEntity user, String  sitesList){
        String[] numberStrings=sitesList.split(",");
        HashSet<Long> sites=new HashSet<>();
        for(String n:numberStrings){
            sites.add(Long.parseLong(n.trim()));
        }
        try {
            if(user.updatePreferences(sites))
                return new ResponseEntity<>(new ResponseMessageDTO("True"),HttpStatus.OK);

        }catch (Exception e){
            log.error(e.getMessage());
        }
          return new ResponseEntity<>(new ResponseMessageDTO("False"),HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Long>> getPreferences(UserEntity user){
        return new ResponseEntity<>(user.getPreferences().stream().toList(),HttpStatus.OK);
    }
    public ResponseEntity<List<UserDTO>> getUser(List<String> userIds) {
        try {
            List<UserEntity> user = userRepository.findByIdIn(userIds).orElseThrow(() -> new EntityNotFoundException("User does not exist"));
            List<UserDTO> userDTOS = user.stream().map(UserMapper.INSTANCE::toDto).toList();
            return ResponseEntity.ok(userDTOS);
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
