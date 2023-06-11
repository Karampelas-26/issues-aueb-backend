package com.aueb.issues.web.controller;

import com.aueb.issues.model.entity.UserEntity;
import com.aueb.issues.web.dto.ApplicationDTO;
import com.aueb.issues.web.dto.NotificationDTO;
import com.aueb.issues.web.dto.ResponseMessageDTO;
import com.aueb.issues.web.dto.TeacherApplicationsDTO;
import com.aueb.issues.web.service.ApplicationService;
import com.aueb.issues.web.service.NotificationService;
import com.aueb.issues.web.service.UserService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
    @Autowired
    UserService userService;

    @GetMapping(value = "/getApplications", produces = "application/json")
    public ResponseEntity<List<TeacherApplicationsDTO>> getApplications(Authentication authentication) {
        return  applicationService.getTeacherApplications((UserEntity) authentication.getPrincipal());
    }
    @GetMapping(value="/getApplicationsByStatus")
    public ResponseEntity<List<TeacherApplicationsDTO>> getApplicationsByStatus(@RequestParam(value = "status", required = true)String status, Authentication authentication){
        return  applicationService.getTeacherApplicationsByStatus((UserEntity) authentication.getPrincipal(),status);
    }
    @GetMapping(value="/notifications", produces = "application/json")
    public ResponseEntity<List<NotificationDTO>> getNotifications(){
        return notificationService.getNotifications();
    }

    @PostMapping(value = "/panic")
    public ResponseEntity<String> panicButton(@RequestBody String siteId){
        return notificationService.panicButton(siteId);
    }

    @GetMapping(value = "/setPreferences")
    public ResponseEntity<ResponseMessageDTO> setPreferences(@RequestParam("sites") List<String> sites, Authentication authentication){
        return userService.setPreferences((UserEntity) authentication.getPrincipal(), sites);
    }
    @GetMapping(value = "/getPreferences")
    public ResponseEntity<List<String>> getPreferences(Authentication authentication){
        return userService.getPreferences ((UserEntity) authentication.getPrincipal());
    }

}
