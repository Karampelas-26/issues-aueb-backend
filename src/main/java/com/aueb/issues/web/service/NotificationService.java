package com.aueb.issues.web.service;

import com.aueb.issues.web.NotificationDTO;
import com.aueb.issues.web.dto.ApplicationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class NotificationService {
    public ResponseEntity<List<NotificationDTO>> getNotifications(){
        return ResponseEntity.ok(new ArrayList());
    }
    public ResponseEntity<ApplicationResponse> panicButton(String siteId) {
        //TODO:CREATE A EVENT LISTENER THAT POPS A WINDOW IN COMMITEE USER
        return ResponseEntity.ok(new ApplicationResponse(LocalDateTime.now(),"OK"));
    }

}
