package com.aueb.issues.web.comittee;

import com.aueb.issues.web.dto.ApplicationsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author George Karampelas
 */

@Controller
@RequestMapping("/committee")
@CrossOrigin(origins = "http://localhost:4200")
@PreAuthorize("hasRole('ADMIN')")
public class ComitteeController {

    @Autowired
    ComitteeService comitteeService;

    @GetMapping("/applications")
    public ResponseEntity<ApplicationsResponse> getApplications() {
        return ResponseEntity.ok(this.comitteeService.getApplications());
    }

    //TODO

    @GetMapping("/hi")
    public ResponseEntity<String> sayHi(){
        return ResponseEntity.ok("hello from another endpoint");
    }
}
