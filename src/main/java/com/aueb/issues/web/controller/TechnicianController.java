package com.aueb.issues.web.controller;

import com.aueb.issues.model.entity.ApplicationEntity;
import com.aueb.issues.model.enums.IssueType;
import com.aueb.issues.model.enums.Priority;
import com.aueb.issues.model.enums.Status;
import com.aueb.issues.model.mapper.ApplicationMapper;
import com.aueb.issues.model.services.SiteService;
import com.aueb.issues.repository.ApplicationRepository;
import com.aueb.issues.repository.SitesRepository;
import com.aueb.issues.web.dto.ApplicationDTO;
import com.aueb.issues.web.service.ApplicationService;
import com.aueb.issues.web.service.BuildingsService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/technician")
@RequiredArgsConstructor
@CrossOrigin
@PreAuthorize("hasRole('TECHNICIAN')")
@Slf4j
public class TechnicianController {

    @Autowired
    ApplicationService applicationService;
    @Autowired
    SiteService siteService;
    @Autowired
    BuildingsService buildingsService;

    @GetMapping("/getAllApplications")
    public ResponseEntity<List<ApplicationDTO>> getAllΑpplications(){
        return applicationService.getAllApplications();
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

    @GetMapping("/filtered-applications-s-values")
    public ResponseEntity<List<ApplicationDTO>> getFilteredApplicationsBySignleValues(@RequestParam(value = "site_name", required = false) String siteName,
                                                                       @RequestParam(value = "priority", required=false) String priority,
                                                                       @RequestParam(value= "issue_type", required = false)String issueType,
                                                                       @RequestParam(value = "status",required = false) String status){
        return applicationService.getApplicationsBySingleValues(siteName,priority,issueType,status);
    }

    @GetMapping("/getSitesName")
    public ResponseEntity<List<String>> getSitesName(){
        return siteService.getAllSitesName();
    }

    @GetMapping("/getBuildingsName")
    public ResponseEntity<List<String>> getBuildingsName(){
        return buildingsService.getAllBuildingsName();
    }

}
