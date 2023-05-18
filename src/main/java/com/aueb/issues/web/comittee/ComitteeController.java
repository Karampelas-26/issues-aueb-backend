package com.aueb.issues.web.comittee;

import com.aueb.issues.web.dto.ApplicationsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
public class ComitteeController {

    @Autowired
    ComitteeService comitteeService;

    @GetMapping("/applications")
    public ResponseEntity<ApplicationsResponse> getApplications() {
        return ResponseEntity.ok(this.comitteeService.getApplications());
    }

    //TODO

}
