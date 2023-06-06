package com.aueb.issues.web.controller;


import com.aueb.issues.model.entity.UserEntity;
import com.aueb.issues.web.dto.UserDTO;
import com.aueb.issues.web.service.BuildingService;
import com.aueb.issues.web.service.SiteService;
import com.aueb.issues.web.dto.ApplicationDTO;
import com.aueb.issues.web.dto.BuildingDTO;
import com.aueb.issues.web.dto.SiteDTO;
import com.aueb.issues.web.service.ApplicationService;
import com.aueb.issues.web.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
    @Autowired
    UserService userService;
    @GetMapping("/filtered-applications-s-values")
    public ResponseEntity<List<ApplicationDTO>> getFilteredApplicationsBySingleValues(@RequestParam(value = "site_name", required = false) String siteName,
                                                                                      @RequestParam(value = "priority", required=false) String priority,
                                                                                      @RequestParam(value= "issue_type", required = false)String issueType,
                                                                                      @RequestParam(value = "status",required = false) String status,
                                                                                      @RequestParam(value = "buildingName",required = false)String buildingName,
                                                                                      Authentication authentication){
        return applicationService.getApplicationsBySingleValues((UserEntity) authentication.getPrincipal(),siteName, priority,issueType,status,buildingName);
    }

    @GetMapping("/getUsersByTechTeam")
    public ResponseEntity<List<UserDTO>> getUsersByTechTeam(@RequestParam(value= "issue_type", required = true) String issueType){
        return userService.getUserByTechTeam(issueType);
    }

    @GetMapping("/getSitesName")
    public ResponseEntity<List<String>> getSitesName(){
        return siteService.getAllSitesName();
    }
    @GetMapping("/all-sites")
    public ResponseEntity<List<SiteDTO>> getAllSites(){
        return siteService.getAllSites();
    }

    @GetMapping("/all-buildings")
    public ResponseEntity<List<BuildingDTO>> getAllBuildings(){
        return buildingService.getAllBuildings();
    }

    @GetMapping("/getBuildingsName")
    public ResponseEntity<List<String>> getBuildingsName() {
        return buildingService.getAllBuildingsName();
    }

    @GetMapping("/getBuildingsSitesName")
    public ResponseEntity<Map<String, List<String>>> getBuildingSitesName(){
        return buildingService.getBuildinsSitesName();
    }

}
