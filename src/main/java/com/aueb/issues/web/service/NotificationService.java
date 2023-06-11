package com.aueb.issues.web.service;

import com.aueb.issues.email.EmailService;
import com.aueb.issues.model.entity.NotificationsEntity;
import com.aueb.issues.model.entity.UserEntity;
import com.aueb.issues.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
public class NotificationService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    EmailService emailService;
    private static  final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final String SUBJECT_COMPLETION="AUTECH-ΟΛΟΚΛΗΡΩΣΗ ΑΙΤΗΣΗΣ";
    public static final String SUBJECT_REJECTION="AUTECH-ΑΠΟΡΡΙΨΗ ΑΙΤΗΣΗΣ";
    public static final String SUBJECT_DUEDATE="AUTECH-ΑΛΛΑΓΗ ΠΡΟΘΕΣΜΙΑΣ ΑΙΤΗΣΗΣ";
    public static final String SUBJECT_CREATE="AUTECH-ΔΗΜΙΟΥΡΓΙΑ ΑΙΤΗΣΗΣ";
    public static final String STATUS_COMPLETION="Η κατάσταση της αίτησης με τίτλο: %s άλλαξε σε <<ΟΛΟΚΛΗΡΩΜΕΝΗ>>.";
    public static final String STATUS_REJECTED="Η  αίτηση με τίτλο: %s Απορρίφθηκε.";
    public static final String APPLICATION_CREATED_PREF="Μια αίτηση δημιουργήθηκε για την αίθουσα %s , με τίτλο: %s. Λάβατε αυτή την ειδοποιηση" +
            "διότι αυτή η αίθουσα έχει δηλωθεί στις προτιμίσεις.";
    public static final String DUE_UPDATE="Η ημερομηνία αναμενόμενης ολοκλήρωσης της αίτησης με τίτλο: %s άλλαξε σε %s.";
    public void addCompletedNotification(UserEntity user, String id){
        NotificationsEntity notification=NotificationsEntity.builder()
                .dateNotification(LocalDateTime.now())
                        .content(String.format(STATUS_COMPLETION,id))
                .build();
        user.getNotifications().add(notification);
        userRepository.save(user);
        emailService.sendEmail(user.getEmail(),SUBJECT_COMPLETION,notification.getContent());
    }
    public void addRejectedNotification(UserEntity user,String id){
    NotificationsEntity notification=NotificationsEntity.builder()
                .dateNotification(LocalDateTime.now())
                .content(String.format(STATUS_REJECTED,id))
                .build();
        user.getNotifications().add(notification);
        userRepository.save(user);
        emailService.sendEmail(user.getEmail(),SUBJECT_REJECTION,notification.getContent());
    }
    public void addDueDateNotification(UserEntity user,String id,LocalDateTime due){
        NotificationsEntity notification=
                NotificationsEntity.builder()
                .dateNotification(LocalDateTime.now())
                .content(String.format(DUE_UPDATE,id,due.format(formatter)))
                .build();
        user.getNotifications().add(notification);
        userRepository.save(user);
        emailService.sendEmail(user.getEmail(),DUE_UPDATE,notification.getContent());
    }
    public void addCreatedOnSiteNotification(String site, String title){
        List<UserEntity> teachers=userRepository.findTeachers();
        for(UserEntity teacher:teachers){
            if(teacher.getPreferences().contains(site)){
                NotificationsEntity notification=
                        NotificationsEntity.builder()
                                .dateNotification(LocalDateTime.now())
                                .content(String.format(APPLICATION_CREATED_PREF,site,title))
                                .build();
                teacher.getNotifications().add(notification);
                userRepository.save(teacher);
                emailService.sendEmail(teacher.getEmail(),SUBJECT_CREATE,notification.getContent());
            }
        }
    }

}
