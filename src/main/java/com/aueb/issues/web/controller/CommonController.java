package com.aueb.issues.web.controller;

import com.aueb.issues.model.entity.EquipmentEntity;
import com.aueb.issues.model.entity.UserEntity;
import com.aueb.issues.web.dto.ApplicationDTO;
import com.aueb.issues.web.dto.CommentDTO;
import com.aueb.issues.web.dto.ResponseMessageDTO;
import com.aueb.issues.web.dto.SiteDTO;
import com.aueb.issues.web.service.ApplicationService;
import com.aueb.issues.web.service.BuildingService;
import com.aueb.issues.web.service.SiteService;
import com.aueb.issues.web.dto.*;
import com.aueb.issues.web.service.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
@RequestMapping("/common")
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class CommonController {

    @Autowired
    ApplicationService applicationService;
    @Autowired
    SiteService siteService;
    @Autowired
    BuildingService buildingService;
    @Autowired
    UserService userService;
    @Autowired
    EquipmentService equipmentService;

    @PreAuthorize("hasAnyRole('COMMITTEE', 'TECHNICIAN')")
    @GetMapping("/getAllApplications")
    public ResponseEntity<List<ApplicationDTO>> getApplications(Authentication authentication){
        return applicationService.getAllApplications((UserEntity) authentication.getPrincipal());
    }
    @PreAuthorize("hasAnyRole('COMMITTEE', 'TECHNICIAN')")
    @GetMapping("/filtered-applications-s-values")
    public ResponseEntity<List<ApplicationDTO>> getFilteredApplicationsBySingleValues(@RequestParam(value = "site_name", required = false) String siteName,
                                                                                      @RequestParam(value = "priority", required=false) String priority,
                                                                                      @RequestParam(value= "issue_type", required = false)String issueType,
                                                                                      @RequestParam(value = "status",required = false) String status,
                                                                                      @RequestParam(value = "buildingName",required = false)String buildingName,
                                                                                      Authentication authentication){
        return applicationService.getApplicationsBySingleValues((UserEntity) authentication.getPrincipal(),siteName, priority,issueType,status,buildingName);
    }

    @PreAuthorize("hasAnyRole('COMMITTEE', 'TECHNICIAN', 'TEACHER')")
    @GetMapping("/getEquipment")
    public ResponseEntity<List<EquipmentDTO>> getEquipment(){
        return equipmentService.getEquipment();
    }

    @PreAuthorize("hasAnyRole('COMMITTEE', 'TECHNICIAN', 'TEACHER')")
    @DeleteMapping("/deleteEquipment/{equipmentId}")
    public ResponseEntity<ResponseMessageDTO> deleteEquipment(@PathVariable("equipmentId") Long id){
        return equipmentService.deleteEquipment(id);
    }
    @PreAuthorize("hasAnyRole('COMMITTEE', 'TECHNICIAN')")
    @GetMapping("/completeApplication")
    public ResponseEntity<ResponseMessageDTO> completeApplication(@RequestParam(value = "id") String id){
        return applicationService.completeApplication(id);
    }

    @PreAuthorize("hasAnyRole('COMMITTEE', 'TECHNICIAN', 'TEACHER')")
    @PostMapping(value = "/submit-new-issue")
    public ResponseEntity<ResponseMessageDTO> submitApplication(@RequestBody ObjectNode node, Authentication authentication) {
        return applicationService.submitApplication(node, (UserEntity) authentication.getPrincipal());
    }


    @PreAuthorize("hasAnyRole('COMMITTEE', 'TECHNICIAN')")
    @GetMapping("/getUsersByIds")
    public ResponseEntity<List<UserDTO>> getUsers(@RequestParam(value = "usersIds") List<String> usersIds) {
        return userService.getUser(usersIds);
    }

    @PreAuthorize("hasAnyRole('COMMITTEE', 'TECHNICIAN')")
    @PutMapping("/update")
    public ResponseEntity<ApplicationDTO> updateIssue(@RequestBody ApplicationDTO request){
        return applicationService.updateApplication(request);
    }
    @PreAuthorize("hasAnyRole('COMMITTEE')")
    @DeleteMapping("/delete{issueid}")
    public ResponseEntity<String> completeIssue(@PathVariable("issueid") String id){
        return applicationService.deleteIssue(id);
    }

    @GetMapping("/getUsersByTechTeam")
    public ResponseEntity<List<UserDTO>> getUsersByTechTeam(@RequestParam(value= "issue_type", required = true) String issueType){
        return userService.getUserByTechTeam(issueType);
    }

    //comments
    @PreAuthorize("hasAnyRole('COMMITTEE', 'TECHNICIAN')")
    @PostMapping("/add_comment")
    public ResponseEntity<CommentDTO> addComment(@RequestParam(value = "issue_id", required = true) String issueId, @RequestBody String commentDto, Authentication authentication) {
        return applicationService.createComment(issueId, commentDto, (UserEntity) authentication.getPrincipal());
    }

    @PreAuthorize("hasAnyRole('COMMITTEE', 'TECHNICIAN','TEACHER')")
    @GetMapping("/getPersonalInfo")
    public ResponseEntity<UserDTO> getPersonalInfo(Authentication authentication) {
        return userService.getPersonalInfo((UserEntity) authentication.getPrincipal());
    }
    //get Static Data
    @PreAuthorize("hasAnyRole('COMMITTEE', 'TECHNICIAN', 'TEACHER')")
    @GetMapping("/getSitesName")
    public ResponseEntity<List<String>> getSitesName(){
        return siteService.getAllSitesName();
    }
    @PreAuthorize("hasAnyRole('COMMITTEE', 'TECHNICIAN', 'TEACHER')")
    @GetMapping("/all-sites")
    public ResponseEntity<List<SiteDTO>> getAllSites(){
        return siteService.getAllSites();
    }
    @PreAuthorize("hasAnyRole('COMMITTEE', 'TECHNICIAN', 'TEACHER')")
    @GetMapping("/getBuildingsSitesName")
    public ResponseEntity<Map<String, List<String>>> getBuildingSitesName(){
        return buildingService.getBuildinsSitesName();
    }
    @PreAuthorize("hasAnyRole('COMMITTEE', 'TECHNICIAN', 'TEACHER')")
    @GetMapping("/getBuildingsName")
    public ResponseEntity<List<String>> getBuildingsName() {
        return buildingService.getAllBuildingsName();
    }
    @PreAuthorize("hasAnyRole('COMMITTEE', 'TECHNICIAN', 'TEACHER')")
    @GetMapping("/staticEnums")
    public ResponseEntity<Map<String,List<String>>> getStaticData(){
        return applicationService.getStaticData();
    }

    @PreAuthorize("hasAnyRole('COMMITTEE', 'TECHNICIAN', 'TEACHER')")
    @GetMapping("/getEquipmentsOfSiteName")
    public ResponseEntity<List<EquipmentDTO>> getEquipmentsOfSiteName(@RequestParam(value = "siteName", required = true) String sitename){
        return siteService.getEquipmentOfSite(sitename);
    }
}
