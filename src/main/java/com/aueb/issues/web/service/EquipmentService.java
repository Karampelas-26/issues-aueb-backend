package com.aueb.issues.web.service;

import com.aueb.issues.model.entity.ApplicationEntity;
import com.aueb.issues.model.entity.EquipmentEntity;
import com.aueb.issues.model.entity.UserEntity;
import com.aueb.issues.model.enums.Role;
import com.aueb.issues.model.services.BuildingService;
import com.aueb.issues.model.services.SiteService;
import com.aueb.issues.repository.EquipmentRepository;
import com.aueb.issues.web.dto.EquipmentDTO;
import com.aueb.issues.web.dto.TeacherApplicationsDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class EquipmentService {
    @Autowired
    EquipmentRepository equipmentRepository;
    @Autowired
    BuildingService buildingService;
    @Autowired
    SiteService siteService;
    LocalDateTime dateTime = LocalDateTime.now();
    public ResponseEntity<String> createEquipment(EquipmentDTO request) {
        try{
            EquipmentEntity equipment = EquipmentEntity.builder()
                    .id(String.valueOf(UUID.randomUUID()))
                    .building(buildingService.getBuildingById(request.getBuildingId()))
                    .site(siteService.getSiteBySiteId(request.getSiteId()))
                    .typeOfEquipment(request.getTypeOfEquipment())
                    .installationDate(dateTime)
                    .build();
            equipmentRepository.save(equipment);

        }catch(Exception e){
            log.error(e.toString());
        }

        return ResponseEntity.ok(null);
    }

    public ResponseEntity<List<EquipmentDTO>> getEquipment() {
        List<EquipmentDTO> eq = new ArrayList<>();
        try{
            List<EquipmentEntity> equipment = equipmentRepository.findAll();
            ObjectMapper mapper = new ObjectMapper();
            for (EquipmentEntity equip: equipment){
                mapper.convertValue(equip,TeacherApplicationsDTO.class);
            }
        }catch (Exception e){
            log.error(e.toString());
            return null;
        }
        return (ResponseEntity<List<EquipmentDTO>>) eq;
    }

    public ResponseEntity<String> updateEquipment(String id, EquipmentDTO request) {
        try{

            EquipmentEntity equipment = equipmentRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Application not found"));
            if(equipment!=null) {
                return ResponseEntity.badRequest().body("No equipment with such id");
            }
            if(request.getSiteId()!=null)
                equipment.setSite(siteService.getSiteBySiteId(request.getSiteId()));
            if(request.getBuildingId()!=0)
                equipment.setBuilding(buildingService.getBuildingById((request.getBuildingId())));
            if(request.getTypeOfEquipment()!=null)
                equipment.setTypeOfEquipment(request.getTypeOfEquipment());
            return ResponseEntity.ok(null);

        }catch (Exception e){
            log.error(e.toString());
            return null;
        }
    }

    public ResponseEntity<String> deleteEquipment(String id) {
        try{
            equipmentRepository.findById(id).orElseThrow(()->new EntityNotFoundException("No such entity found"));
            equipmentRepository.deleteById(id);
            return ResponseEntity.ok(null);
        }catch (Exception e){
            log.error(e.toString());
            return null;
        }
    }
}
