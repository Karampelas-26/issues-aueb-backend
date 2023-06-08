package com.aueb.issues.web.service;

import com.aueb.issues.model.entity.EquipmentEntity;
import com.aueb.issues.model.mapper.EquipmentMapper;
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
//                    .id(String.valueOf(UUID.randomUUID()))
//                    .building(buildingService.getBuildingById(request.getBuildingId()))
//                    .site(siteService.getSiteBySiteId(request.getSiteId()))
                    .typeOfEquipment(request.getTypeOfEquipment())
                    .build();
            equipmentRepository.save(equipment);

        }catch(Exception e){
            log.error(e.toString());
        }

        return ResponseEntity.ok(null);
    }

    public ResponseEntity<List<EquipmentDTO>> getEquipment() {
        try{
           List<EquipmentEntity> eq = equipmentRepository.findAll();
           return ResponseEntity.ok(toDTO(eq));
        }catch (Exception e){
            log.error(e.toString());
            return null;
        }

    }

    public ResponseEntity<String> updateEquipment(String id, EquipmentDTO request) {
        try{

            EquipmentEntity equipment = equipmentRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Application not found"));
            if(equipment!=null) {
                return ResponseEntity.badRequest().body("No equipment with such id");
            }
//            if(request.getSiteId()!=null)
////                equipment.setSite(siteService.getSiteBySiteId(request.getSiteId()));
//            if(request.getBuildingId()!=0)
////                equipment.setBuilding(buildingService.getBuildingById((request.getBuildingId())));
//            if(request.getTypeOfEquipment()!=null)
////                equipment.setTypeOfEquipment(request.getTypeOfEquipment());
            return ResponseEntity.ok(null);

        }catch (Exception e){
            log.error(e.toString());
            return null;
        }
    }

    public ResponseEntity<String> deleteEquipment(String id) {
        try{
            equipmentRepository.findById(id).orElseThrow(()->{new EntityNotFoundException("No such entity found");
            return null;});
            equipmentRepository.deleteById(id);
            return ResponseEntity.ok(null);
        }catch (Exception e){
            log.error(e.toString());
            return null;
        }
    }

    public List<EquipmentDTO> toDTO(List<EquipmentEntity> entities){
        return entities.stream().map(EquipmentMapper.INSTANCE::toDTO).toList();
    }
}
