package com.aueb.issues.web.controller;

import com.aueb.issues.model.entity.ApplicationEntity;
import com.aueb.issues.model.mapper.ApplicationMapper;
import com.aueb.issues.repository.ApplicationRepository;
import com.aueb.issues.web.dto.ApplicationDTO;
import com.aueb.issues.web.service.ApplicationService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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
    private final ApplicationRepository applicationRepository;

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

    @GetMapping("/specific")
    public ResponseEntity<List<ApplicationDTO>> getApplicationsFilters(@RequestParam(value = "site_name", required = false) String siteName) {
        try {
            log.info(siteName);
            List<ApplicationEntity> q = applicationRepository.findByValues(siteName,null,null,null);
            List<ApplicationDTO> ret = q.stream().map(ApplicationMapper.INSTANCE::toDTO).collect(Collectors.toList());
            return ResponseEntity.ok(ret);
        }
        catch (Exception e){
            log.error(e.toString());
        }
        return ResponseEntity.internalServerError().body(null);
    }
//    @GetMapping("/body")
//    public ResponseEntity<List<ApplicationDTO>> getMultipleFilters(@RequestBody ObjectNode objectNode){
//
//    }




    @GetMapping("/hi")
    public String getHi(){
        return "hi";
    }

}
