package com.aueb.issues.web.controller;

import com.aueb.issues.model.entity.BuildingEntity;
import com.aueb.issues.model.entity.EquipmentEntity;
import com.aueb.issues.model.entity.UserEntity;
import com.aueb.issues.web.comittee.CreateUserDTO;
import com.aueb.issues.web.dto.*;
import com.aueb.issues.web.service.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author George Karampelas
 */

@Controller
@RequestMapping("/committee")
@CrossOrigin(origins = "http://localhost:4200/")
@PreAuthorize("hasRole('COMMITTEE')")
public class CommitteeController {
    @Autowired
    ApplicationService applicationService;
    @Autowired
    UserService userService;
    @Autowired
    BuildingService buildingsService;
    @Autowired
    EquipmentService equipmentService;
    @Autowired
    SiteService siteService;
    @Autowired
    StatisticsService statisticsService;
    @PostMapping("/create-user")
    public ResponseEntity<ResponseMessageDTO> createUser(@RequestBody CreateUserDTO request) {
        return userService.createUser(request);
    }

    @PostMapping("/upload-users")
    public ResponseEntity<ResponseMessageDTO> uploadUsers(@RequestParam("file") MultipartFile file) {
        return userService.createUserCsv(file);
    }

    @GetMapping("/get-users")
    public ResponseEntity<List<UserDTO>> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/getTechnicalTeams")
    public ResponseEntity<Map<String, List<UserDTO>>> getTechTeamWUser(){
        return userService.getTechTeamsWUsers();
    }
    @GetMapping("/getTechsWithoutTeams")
    public ResponseEntity<List<UserDTO>> getTechsWOTeams(){
        return userService.getUsersWOTechTeam();
    }

    @PutMapping("/update-user")
    public ResponseEntity<ResponseMessageDTO> updateUser(@RequestBody UserDTO request) {
        return userService.updateUser(request);
    }

    @DeleteMapping("/delete-user/{userId}")
    public ResponseEntity<ResponseMessageDTO> deleteUser(@PathVariable("userId") String id) {
        return userService.deleteUser(id);
    }

    @PostMapping("/createBuilding")
    public ResponseEntity<ResponseMessageDTO> createBuilding(@RequestBody BuildingDTO requestDTO) {
        return buildingsService.createBuilding(requestDTO);
    }

    @GetMapping("/getBuilding")
    public ResponseEntity<List<BuildingDTO>> getBuildings() {
        return buildingsService.getBuildings();
    }@GetMapping("/getNothing")
    public ResponseEntity<ResponseMessageDTO> getBuilding() {
        return ResponseEntity.ok(new ResponseMessageDTO("sdaf"));
    }

    @PutMapping("/updateBuilding/{buildingId}")
    public ResponseEntity<String> updateBuilding(@PathVariable("buildingId") int id,
                                                 @RequestBody BuildingDTO request) {
        return buildingsService.updateBuilding(id, request);
    }

    @DeleteMapping("deleteBuilding/{buildingId}")
    public ResponseEntity<String> deleteBuilding(@PathVariable("buildingId") int id) {
        return buildingsService.deleteBuilding(id);
    }

    @GetMapping("/getSiteContainEquipment")
    public ResponseEntity<Map<String, List<String>>> getSitesByEquipmentType(@RequestParam("typeOfEquipment") String typeOfEquipment) {

        return siteService.getSiteByBuildingAndEquipmentType(typeOfEquipment);
    }

    @PutMapping("/deleteEquipmentsOnSite/{typeOfEquipment}")
    public ResponseEntity<ResponseMessageDTO> deleteEquipmentsOnSites(@PathVariable("typeOfEquipment") Long typeOfEquipment, @RequestBody List<String> sitesName) {
        return siteService.deleteEquipmentsOnSites(typeOfEquipment, sitesName);
    }
    @PutMapping("/addEquipmentsOnSite/{typeOfEquipment}")
    public ResponseEntity<ResponseMessageDTO> addEquipmentsOnSites(@PathVariable("typeOfEquipment") Long typeOfEquipment, @RequestBody List<String> sitesName) {
        return siteService.addEquipmentsOnSites(typeOfEquipment, sitesName);
    }

    @PostMapping("/createEquipment")
    public ResponseEntity<ResponseMessageDTO> createEquipment(@RequestBody String equipment){
        return equipmentService.createEquipment(equipment);
    }

    @PutMapping("/updateEquipment/{equipmentId}")
    public ResponseEntity<String> updateEquipment(@PathVariable("equipmentId") Long id,
                                                  @RequestBody EquipmentDTO request) {
        return equipmentService.updateEquipment(id, request);
    }

    @PostMapping("/createSites")
    public ResponseEntity<ResponseMessageDTO> createSites(@RequestBody CreateBuildingRequest request) {
        return siteService.createSites(request);
    }

    @GetMapping("/all-sites")
    public ResponseEntity<List<SiteDTO>> getAllSites() {
        return siteService.getAllSites();
    }

    //stats
    @GetMapping("/statistics/getApplicationsByMonth")
    public ResponseEntity<StatPojo> getApplicationsByMonth(@RequestParam(value = "buildingId",required = false) Long buildingId,
                                                                            @RequestParam(value = "createStart",required = false)
                                                                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime createStart,
                                                                            @RequestParam(value = "createEnd",required = false)
                                                                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime createEnd,
                                                                            @RequestParam(value = "issueType",required = false)String issueType){
        return statisticsService.getAllStatsByMonth(buildingId,issueType,createStart,createEnd);
    }

    @GetMapping("/downloadStatistics")
    public ResponseEntity<Resource> downloadStatistics(){
        return statisticsService.downloadStats();
    }

}
