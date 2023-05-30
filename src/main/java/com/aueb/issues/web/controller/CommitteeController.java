package com.aueb.issues.web.controller;

import com.aueb.issues.web.comittee.CreateUserDTO;
import com.aueb.issues.web.comittee.CreateUserResponse;
import com.aueb.issues.web.dto.ApplicationDTO;
import com.aueb.issues.web.dto.BuildingDTO;
import com.aueb.issues.web.dto.UserDTO;
import com.aueb.issues.web.service.ApplicationService;
import com.aueb.issues.web.service.BuildingsService;
import com.aueb.issues.web.service.UserService;
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
@CrossOrigin(origins = "http://localhost:4200")
@PreAuthorize("hasRole('ADMIN')")
public class CommitteeController {
    @Autowired
    ApplicationService applicationService;
    @Autowired
    UserService userService;
    @Autowired
    BuildingsService buildingsService;
    @GetMapping(value = "/getApplications", produces = "application/json")
    public ResponseEntity<List<ApplicationDTO>> getApplications() {
        return applicationService.getApplications();
    }
    //TODO: Methods for Statistics, getBuildings, getUsers, getEquipment,
    @PostMapping("/create-user")
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody CreateUserDTO request) {
        return userService.createUser(request);
    }
    @GetMapping("/getUsers")
    public ResponseEntity<List<UserDTO>> getUsers(){
        return userService.getUsers();
    }

    @PutMapping("/updateuser/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable("userId") Long id,
                                             @RequestBody UserDTO request){
        return userService.updateUser(id,request);
    }
    @DeleteMapping("/deleteuser/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") Long id){
        return userService.deleteUser(id);
    }
    @PostMapping(value ="/submit-new-issue")
    public ResponseEntity<String> submitApplication(@RequestBody ApplicationDTO requestDTO){
        return applicationService.submitApplication(requestDTO);
    }
    @PostMapping("/create-building")
    public ResponseEntity<String> createBuilding(@RequestBody BuildingDTO requestDTO){
        return buildingsService.createBuilding(requestDTO);
    }

    @GetMapping("/getBuilding")
    public ResponseEntity<List<BuildingDTO>> getBuildings(){
        return buildingsService.getBuildings();
    }

    @PutMapping("/updatebuilding/{buildingId}")
    public ResponseEntity<String> updateBuilding(@PathVariable("buildingId") int id,
            @RequestBody BuildingDTO request){
        return buildingsService.updateBuilding(id,request);
    }

    @DeleteMapping("deleteBuilding/{buildingId}")
    public ResponseEntity<String> deleteBuilding(@PathVariable("buildingId") int id){
        return buildingsService.deleteBuilding(id);
    }
}
