package com.aueb.issues.web.comittee;

import com.aueb.issues.web.dto.ApplicationDTO;
import com.aueb.issues.web.dto.ApplicationResponse;
import com.aueb.issues.web.dto.TeacherApplicationsDTO;
import com.aueb.issues.web.service.ApplicationsService;
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
public class ComitteeController {

    @Autowired
    ApplicationsService applicationsService;


    @GetMapping("/applications")
    public ResponseEntity<List<ApplicationDTO>> getApplications() {
        return applicationsService.getApplications();
    }

    //TODO: Statistics, User import, building import, new application
    @GetMapping("/hi")
    public ResponseEntity<String> sayHi(){
        return ResponseEntity.ok("hello from another endpoint");
    }


    @PostMapping(value ="/submit-new-issue")
    public ResponseEntity<ApplicationResponse> submitApplication(@RequestBody ApplicationDTO requestDTO){
        return applicationsService.submitApplication(requestDTO);
    }
}
