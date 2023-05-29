package com.aueb.issues.web.technician;

import com.aueb.issues.model.entity.ApplicationEntity;
import com.aueb.issues.model.technician.ApplicationRequest;
import com.aueb.issues.model.technician.ApplicationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/technician")
@RequiredArgsConstructor
@CrossOrigin
/*@PreAuthorize("hasRole('TECHNICIAN')")*/

public class TechnicianController {
    private final TechnicianService technicianService;

    @GetMapping
    public ResponseEntity<ApplicationResponse> getIssues(){
        return ResponseEntity.ok(technicianService.response());
    }

    @PutMapping("{issueid}")
    public ResponseEntity<ApplicationResponse> updateIssue(@PathVariable("issueid") String id,
                                                           @RequestBody ApplicationRequest request){
        return ResponseEntity.ok(technicianService.update(id, request));
    }

    @DeleteMapping("/{issueid}")
    public ResponseEntity<ApplicationResponse> completeIssue(@PathVariable("issueid") String id,
                                                             @RequestBody ApplicationRequest request){
        return ResponseEntity.ok(technicianService.deleteIssue(id,request));
    }

}
