package com.aueb.issues.web.controller;

import com.aueb.issues.web.comittee.CreateUserDTO;
import com.aueb.issues.web.comittee.CreateUserResponse;
import com.aueb.issues.web.dto.*;
import com.aueb.issues.web.service.ApplicationService;
import com.aueb.issues.web.service.BuildingsService;
import com.aueb.issues.web.service.EquipmentService;
import com.aueb.issues.web.service.UserService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.SplittableRandom;

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
    BuildingsService buildingsService;
    @Autowired
    EquipmentService equipmentService;

    @GetMapping(value = "/getApplications", produces = "application/json")
    public ResponseEntity<List<ApplicationDTO>> getApplications(@RequestBody ObjectNode requestData) {
        return applicationService.getApplications(requestData);
    }
    //TODO: Methods for Statistics, getBuildings, getUsers, getEquipment,
    @PostMapping("/create-user")
    public ResponseEntity<ResponseMessageDTO> createUser(@RequestBody CreateUserDTO request) {
        return userService.createUser(request);
    }
    @GetMapping("/getUsers")
    public ResponseEntity<List<UserDTO>> getUsers(){
        return userService.getUsers();
    }

    @PutMapping("/update-user/{userId}")
    public ResponseEntity<ResponseMessageDTO> updateUser(@PathVariable("userId") Long id,
                                             @RequestBody UserDTO request){
        return userService.updateUser(id,request);
    }
    @DeleteMapping("/delete-user/{userId}")
    public ResponseEntity<ResponseMessageDTO> deleteUser(@PathVariable("userId") Long id){
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
