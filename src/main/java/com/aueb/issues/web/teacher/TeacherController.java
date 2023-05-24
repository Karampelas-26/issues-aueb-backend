package com.aueb.issues.web.teacher;

import com.aueb.issues.web.*;
import com.aueb.issues.web.dto.TeacherApplicationsDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller devoted on teacher role
 */
@Slf4j
@RestController
@RequestMapping("/teacher")

public class TeacherController {
    @Autowired
    NotificationService notificationService;
    @Autowired
    ApplicationsService applicationsService;

    @GetMapping(value = "/getApplications", produces = "application/json")
    public ResponseEntity<List<TeacherApplicationsDTO>> getApplications() {
        return  applicationsService.getTeacherApplications();
    }
    @GetMapping(value="/notifications", produces = "application/json")
    public ResponseEntity<List<NotificationDTO>> getNotifications(){
        return notificationService.getNotifications();
    }

    @PostMapping(value = "/panic")
    public ResponseEntity<SubmitApplicationResponse> panicButton(@RequestBody String siteId){
        return notificationService.panicButton(siteId);
    }

    @PostMapping(value ="/submit-new-issue")
    public ResponseEntity<SubmitApplicationResponse> submitApplication(@RequestBody SubmitApplicationDTO requestDTO){
        return applicationsService.submitApplication(requestDTO);
    }

}
