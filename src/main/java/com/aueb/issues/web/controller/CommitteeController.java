package com.aueb.issues.web.controller;

import com.aueb.issues.model.entity.UserEntity;
import com.aueb.issues.web.comittee.CreateUserDTO;
import com.aueb.issues.web.dto.*;
import com.aueb.issues.web.service.ApplicationService;
import com.aueb.issues.web.service.BuildingService;
import com.aueb.issues.web.service.EquipmentService;
import com.aueb.issues.web.service.UserService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author George Karampelas
 */

@Controller
@RequestMapping("/committee")
@CrossOrigin(origins = "http://localhost:4200/")
@PreAuthorize("hasRole('ADMIN')")
public class CommitteeController {
    @Autowired
    ApplicationService applicationService;
    @Autowired
    UserService userService;
    @Autowired
    BuildingService buildingsService;
    @Autowired
    EquipmentService equipmentService;

    @GetMapping(value = "/getAllApplications", produces = "application/json")
    public ResponseEntity<List<ApplicationDTO>> getAllApplications() {
        return applicationService.getAllApplications();
    }
    @GetMapping("/filtered-applications-s-values")
    public ResponseEntity<List<ApplicationDTO>> getFilteredApplicationsBySignleValues(@RequestParam(value = "site_name", required = false) String siteName,
                                                                                      @RequestParam(value = "priority", required=false) String priority,
                                                                                      @RequestParam(value= "issue_type", required = false) String issueType,
                                                                                      @RequestParam(value = "status",required = false) String status,
                                                                                      @RequestParam(value = "buildingName",required = false)String buildingName,
                                                                                      Authentication authentication) {
        UserEntity userReq = (UserEntity) authentication.getPrincipal();
        return applicationService.getApplicationsBySingleValues("COMMITTEE" ,userReq,siteName, priority, issueType, status,buildingName);
    }

    //TODO: Methods for Statistics, getBuildings, getUsers, getEquipment,
    @PostMapping("/create-user")
    public ResponseEntity<ResponseMessageDTO> createUser(@RequestBody CreateUserDTO request) {
        return userService.createUser(request);
    }

    @PostMapping("/upload-users")
    public ResponseEntity<ResponseMessageDTO> uploadUsers(@RequestParam("file")MultipartFile file){
        return userService.createUserCsv(file);
    }

    @GetMapping("/get-users")
    public ResponseEntity<List<UserDTO>> getUsers(){
        return userService.getUsers();
    }

    @PutMapping("/update-user")
    public ResponseEntity<ResponseMessageDTO> updateUser(@RequestBody UserDTO request){
        return userService.updateUser(request);
    }

    @DeleteMapping("/delete-user/{userId}")
    public ResponseEntity<ResponseMessageDTO> deleteUser(@PathVariable("userId") String id){
        return userService.deleteUser(id);
    }

    @PostMapping(value ="/submit-new-issue")
    public ResponseEntity<String> submitApplication(@RequestBody ApplicationDTO requestDTO){
        return applicationService.submitApplication(requestDTO);
    }

    @PostMapping("/createBuilding")
    public ResponseEntity<String> createBuilding(@RequestBody BuildingDTO requestDTO){
        return buildingsService.createBuilding(requestDTO);
    }

    @GetMapping("/getBuilding")
    public ResponseEntity<List<BuildingDTO>> getBuildings(){
        return buildingsService.getBuildings();
    }

    @PutMapping("/updateBuilding/{buildingId}")
    public ResponseEntity<String> updateBuilding(@PathVariable("buildingId") int id,
            @RequestBody BuildingDTO request){
        return buildingsService.updateBuilding(id,request);
    }

    @DeleteMapping("deleteBuilding/{buildingId}")
    public ResponseEntity<String> deleteBuilding(@PathVariable("buildingId") int id){
        return buildingsService.deleteBuilding(id);
    }

    @PostMapping("/createEquipment")
    public ResponseEntity<String> createEquipment(@RequestBody EquipmentDTO request){
        return equipmentService.createEquipment(request);
    }

    @GetMapping("/getEquipment")
    public ResponseEntity<List<EquipmentDTO>> getEquipment(){
        return equipmentService.getEquipment();
    }

    @PutMapping("/updateEquipment/{equipmentId}")
    public ResponseEntity<String> updateEquipment(@PathVariable("equipmentId") String id,
                                                  @RequestBody EquipmentDTO request){
        return equipmentService.updateEquipment(id,request);
    }

    @DeleteMapping("/deleteEquipment/{equipmentId}")
    public ResponseEntity<String> deleteEquipment(@PathVariable("equipmentId") String id){
        return equipmentService.deleteEquipment(id);
    }
}
