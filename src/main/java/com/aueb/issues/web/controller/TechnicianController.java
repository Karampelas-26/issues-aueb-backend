package com.aueb.issues.web.controller;

import com.aueb.issues.web.service.BuildingService;
import com.aueb.issues.web.service.SiteService;
import com.aueb.issues.repository.ApplicationRepository;
import com.aueb.issues.web.dto.ApplicationDTO;
import com.aueb.issues.web.dto.BuildingDTO;
import com.aueb.issues.web.dto.SiteDTO;
import com.aueb.issues.web.service.ApplicationService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    BuildingService buildingService;

    private final ApplicationRepository applicationRepository;

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
    public ResponseEntity<List<ApplicationDTO>> getFilteredApplicationsBySingleValues(@RequestParam(value = "site_name", required = false) String siteName,
                                                                       @RequestParam(value = "priority", required=false) String priority,
                                                                       @RequestParam(value= "issue_type", required = false)String issueType,
                                                                       @RequestParam(value = "status",required = false) String status,
                                                                        @RequestParam(value = "buildingName",required = false)String buildingName){
        return applicationService.getApplicationsBySingleValues(siteName,priority,issueType,status,buildingName);
    }

    @GetMapping("/all-sites")
    public ResponseEntity<List<SiteDTO>> getAllSites(){
        return siteService.getAllSites();
    }

    @GetMapping("/all-buildings")
    public ResponseEntity<List<BuildingDTO>> getAllBuildings(){
        return buildingService.getAllBuildings();
    }

    @GetMapping("/staticEnums")
    public ResponseEntity<Map<String,List<String>>> getStaticData(){
        return applicationService.getStaticData();
    }

    /**
     *
     * use method on postman, write the body as such
     * {
     *     "siteName": "Mikhs",
     *     "priority" : "maoys"
     * }
     * and it works
     */
    @GetMapping("/testNodes")
    public ResponseEntity<String> testNodes(@RequestBody ObjectNode node ){
        String siteN="",pri="";
        if(node.get("siteName")!=null) {
            siteN=node.get("siteName").asText();
        }
        if(node.get("priority")!=null)
            pri=node.get("priority").asText();
        return ResponseEntity.ok("siteName "+ siteN+ "\nPriority "+ pri) ;
    }

    @GetMapping("/hi")
    public String getHi(){
        return "hi";
    }

}
