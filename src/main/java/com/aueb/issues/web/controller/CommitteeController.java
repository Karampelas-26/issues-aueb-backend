package com.aueb.issues.web.controller;

import com.aueb.issues.web.comittee.CreateUserDTO;
import com.aueb.issues.web.comittee.CreateUserResponse;
import com.aueb.issues.web.dto.ApplicationDTO;
import com.aueb.issues.web.service.ApplicationService;
import com.aueb.issues.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author George Karampelas
 */

@Controller
@RequestMapping("/committee")
@CrossOrigin(origins = "http://localhost:4200")
@PreAuthorize("hasRole('ADMIN')")
public class CommitteeController {
    @Autowired
    ApplicationService applicationService;
    @Autowired
    UserService userService;

    @GetMapping(value = "/getApplications", produces = "application/json")
    public ResponseEntity<List<ApplicationDTO>> getApplications() {
        return applicationService.getApplications();
    }
    //TODO: Methods for Statistics, getBuildings, getUsers, getEquipment,
    @PostMapping("/create-user")
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody CreateUserDTO request) {
        return userService.createUser(request);
    }

    @PostMapping(value ="/submit-new-issue")
    public ResponseEntity<String> submitApplication(@RequestBody ApplicationDTO requestDTO){
        return applicationService.submitApplication(requestDTO);
    }
}
