package com.aueb.issues.web.service;

import com.aueb.issues.email.EmailService;
import com.aueb.issues.model.entity.NotificationsEntity;
import com.aueb.issues.model.entity.UserEntity;
import com.aueb.issues.model.mapper.NotificationMapper;
import com.aueb.issues.repository.NotificationsEntityRepository;
import com.aueb.issues.repository.UserRepository;

import com.aueb.issues.web.dto.NotificationDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class NotificationService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    EmailService emailService;
    @Autowired
    NotificationsEntityRepository notificationsEntityRepository;
    private static  final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final String SUBJECT_COMPLETION="AUTECH-ΟΛΟΚΛΗΡΩΣΗ ΑΙΤΗΣΗΣ";
    public static final String SUBJECT_REJECTION="AUTECH-ΑΠΟΡΡΙΨΗ ΑΙΤΗΣΗΣ";
    public static final String SUBJECT_CREATE="AUTECH-ΔΗΜΙΟΥΡΓΙΑ ΑΙΤΗΣΗΣ";
    public static final String STATUS_COMPLETION="Η κατάσταση της αίτησης με τίτλο: %s  και αίθουσα <<%s>> άλλαξε σε <<ΟΛΟΚΛΗΡΩΜΕΝΗ>>.";
    public static final String STATUS_REJECTED="Η  αίτηση με τίτλο: %s Απορρίφθηκε.";
    public static final String APPLICATION_CREATED_PREF="Μια αίτηση δημιουργήθηκε για την αίθουσα %s , με τίτλο: %s. Λάβατε αυτή την ειδοποιηση" +
            " διότι αυτή η αίθουσα έχει δηλωθεί στις προτιμίσεις σας.";
    public void addRejectedNotification(UserEntity user,String title){
    NotificationsEntity notification=NotificationsEntity.builder()
                .dateNotification(LocalDateTime.now())
                .content(String.format(STATUS_REJECTED,title))
                .user(user)
                .build();
        user.addNotification(notification);
        userRepository.save(user);
        emailService.sendEmail(user.getEmail(),SUBJECT_REJECTION,notification.getContent());
    }
    public void addCreatedOnSiteNotification(String site, String title){
        List<UserEntity> teachers=userRepository.findTeachers();
        for(UserEntity teacher:teachers){
            if(teacher.getPreferences()==null || teacher.getPreferences().isEmpty())
                continue;
            if(teacher.getPreferences().contains(site)){
                NotificationsEntity notification=
                        NotificationsEntity.builder()
                                .dateNotification(LocalDateTime.now())
                                .content(String.format(APPLICATION_CREATED_PREF,site,title))
                                .user(teacher)
                                .build();
                teacher.addNotification(notification);
                userRepository.save(teacher);
                emailService.sendEmail(teacher.getEmail(),SUBJECT_CREATE,notification.getContent());
            }
        }
    }

    public void addCompletedOnSiteNotification(String siteName, String title){
        List<UserEntity> teachers=userRepository.findTeachers();
        for(UserEntity teacher:teachers){
            if(teacher.getPreferences()==null || teacher.getPreferences().isEmpty())
                continue;

            if(teacher.getPreferences().contains(siteName)){
                NotificationsEntity notification=
                        NotificationsEntity.builder()
                                .dateNotification(LocalDateTime.now())
                                .content(String.format(STATUS_COMPLETION,title,siteName))
                                .user(teacher)
                                .build();
                teacher.addNotification(notification);
                userRepository.save(teacher);
                emailService.sendEmail(teacher.getEmail(),SUBJECT_COMPLETION,notification.getContent());
            }
        }
    }

    public ResponseEntity<List<NotificationDTO>> getNotificationsOfUser(UserEntity user){
        List<NotificationDTO> ret=notificationsEntityRepository.findNotificationsEntitiesByUser(user).stream().map(NotificationMapper.INSTANCE::toDTO).toList();
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }



}
