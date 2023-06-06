package com.aueb.issues.web.controller;

import com.aueb.issues.web.dto.ApplicationDTO;
import com.aueb.issues.web.dto.NotificationDTO;
import com.aueb.issues.web.dto.TeacherApplicationsDTO;
import com.aueb.issues.web.service.ApplicationService;
import com.aueb.issues.web.service.NotificationService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller devoted on teacher role
 */
@Slf4j
@RestController
@RequestMapping("/teacher")
@CrossOrigin(origins = "http://localhost:4200")
@PreAuthorize("hasRole('TEACHER')")
public class TeacherController {
    @Autowired
    NotificationService notificationService;
    @Autowired
    ApplicationService applicationService;

    @GetMapping(value = "/getApplications", produces = "application/json")
    public ResponseEntity<List<TeacherApplicationsDTO>> getApplications() {
        return  applicationService.getTeacherApplications();
    }
    @GetMapping(value="/notifications", produces = "application/json")
    public ResponseEntity<List<NotificationDTO>> getNotifications(){
        return notificationService.getNotifications();
    }

    @PostMapping(value = "/panic")
    public ResponseEntity<String> panicButton(@RequestBody String siteId){
        return notificationService.panicButton(siteId);
    }

}
