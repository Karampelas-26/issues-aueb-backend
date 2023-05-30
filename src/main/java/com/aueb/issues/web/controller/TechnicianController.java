package com.aueb.issues.web.controller;

import com.aueb.issues.web.dto.ApplicationDTO;
import com.aueb.issues.web.service.ApplicationService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/technician")
@RequiredArgsConstructor
@CrossOrigin
@PreAuthorize("hasRole('TECHNICIAN')")
public class TechnicianController {
    @Autowired
    ApplicationService applicationService;

    @GetMapping
    public ResponseEntity<List<ApplicationDTO>> getIssues(@RequestBody ObjectNode node){
        return applicationService.getApplications(node);
    }

    @PutMapping("{issueid}")
    public ResponseEntity<String> updateIssue(@PathVariable("issueid") String id,
                                                           @RequestBody ApplicationDTO request){
        return applicationService.updateApplication(id,request);
    }

    @DeleteMapping("{issueid}")
    public ResponseEntity<String> completeIssue(@PathVariable("issueid") String id){
        return applicationService.deleteIssue(id);
    }

}
