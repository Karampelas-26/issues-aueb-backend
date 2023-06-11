package com.aueb.issues.web.service;

import com.aueb.issues.model.entity.EquipmentEntity;
import com.aueb.issues.model.entity.SiteEntity;
import com.aueb.issues.model.mapper.EquipmentMapper;
import com.aueb.issues.repository.EquipmentRepository;
import com.aueb.issues.repository.SitesRepository;
import com.aueb.issues.web.dto.CreateEquipmentRequest;
import com.aueb.issues.web.dto.EquipmentDTO;
import com.aueb.issues.web.dto.ResponseMessageDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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
    @Autowired
    SitesRepository sitesRepository;
    LocalDateTime dateTime = LocalDateTime.now();
    public ResponseEntity<ResponseMessageDTO> createEquipment(String equipment) {
        try{

            EquipmentEntity eq = EquipmentEntity.builder()
                    .typeOfEquipment(equipment)
                    .build();
            equipmentRepository.save(eq);
            return ResponseEntity.ok(new ResponseMessageDTO());
        }catch(Exception e){
            log.error(e.toString());
            return ResponseEntity.badRequest().body(new ResponseMessageDTO(e.getMessage()));
        }


    }

    public ResponseEntity<List<EquipmentDTO>> getEquipment() {
        try{
           List<EquipmentEntity> eq = equipmentRepository.findAll();
           return ResponseEntity.ok(toDTO(eq));
        }catch (Exception e){
            log.error(e.toString());
            return ResponseEntity.internalServerError().build();
        }

    }

    public ResponseEntity<String> updateEquipment(Long id, EquipmentDTO request) {
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

    public ResponseEntity<ResponseMessageDTO> deleteEquipment(Long id) {
        try{
            equipmentRepository.findById(id).orElseThrow(()->new EntityNotFoundException("No such entity found"));
            equipmentRepository.deleteById(id);
            return ResponseEntity.ok(new ResponseMessageDTO(String.valueOf(id)));
        }catch (Exception e){
            log.error(e.toString());
            return ResponseEntity.badRequest().body(new ResponseMessageDTO(e.getMessage()));
        }
    }

    public List<EquipmentDTO> toDTO(List<EquipmentEntity> entities){
        return entities.stream().map(EquipmentMapper.INSTANCE::toDTO).toList();
    }
}
