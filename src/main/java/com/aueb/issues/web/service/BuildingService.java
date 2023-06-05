package com.aueb.issues.web.service;
import com.aueb.issues.model.entity.BuildingEntity;
import com.aueb.issues.model.entity.SiteEntity;
import com.aueb.issues.model.mapper.BuildingMapper;
import com.aueb.issues.model.mapper.UserMapper;
import com.aueb.issues.repository.BuildingRepository;
import com.aueb.issues.repository.SitesRepository;
import com.aueb.issues.web.dto.ApplicationDTO;
import com.aueb.issues.web.dto.BuildingDTO;
import com.aueb.issues.web.dto.SiteDTO;
import com.aueb.issues.web.dto.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BuildingService {
    @Autowired
    BuildingRepository buildingRepository;

    @Autowired
    SitesRepository sitesRepository;
    @Autowired
    SiteService siteService;

    public ResponseEntity<String> createBuilding(BuildingDTO requestDTO){
        try{
            BuildingEntity buildingEntity = BuildingEntity.builder()
                    .name(requestDTO.getName())
                    .address(requestDTO.getAddress())
                    .floors(requestDTO.getFloors())
                    .build();
            buildingRepository.save(buildingEntity);

        }catch (Exception e){
            log.error(e.toString());
            return null;
        }
        return ResponseEntity.ok(null);
    }

    public ResponseEntity<List<BuildingDTO>> getBuildings() {
        try{
            List<BuildingEntity> buildings = buildingRepository.findAll();

            return ResponseEntity.ok(toDTOWithSites(buildings));
        }catch (Exception e){
            log.error(e.toString());
            return ResponseEntity.internalServerError().build();
        }
    }


    public ResponseEntity<String> updateBuilding(int id, BuildingDTO request) {
        try {
            BuildingEntity building = buildingRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Application not found"));
            if(building!=null) {
                return ResponseEntity.badRequest().body("No issue with such id");
            }
            if(request.getName()!=null)
                building.setName(request.getName());
            if(request.getAddress()!=null)
                building.setAddress(request.getAddress());
            if(building.getFloors()!=0)
                building.setFloors(request.getFloors());
            return ResponseEntity.ok(null);
        }catch (Exception e){
            log.error(e.toString());
            return null;
        }
    }

    public ResponseEntity<String> deleteBuilding(int id){
        try{
            buildingRepository.findById(id).orElseThrow(()->{new EntityNotFoundException("No such entity found");
            return null;});
            buildingRepository.deleteById(id);
            return ResponseEntity.ok(null);
        }catch (Exception e){
            log.error(e.toString());
            return null;
        }
    }

    public ResponseEntity<List<BuildingDTO>> getAllBuildings(){
        return ResponseEntity.ok(toDTO(buildingRepository.findAll()));
    }

    public List<BuildingDTO> toDTOWithSites(List<BuildingEntity> buildings){
        List<BuildingDTO> ret = new ArrayList<>();
        for(BuildingEntity i: buildings){
            Long id = i.getId();
            List<SiteDTO> sites = siteService.toDto(sitesRepository.getsAllSitesOfBuilding(id));

            BuildingDTO s = BuildingMapper.INSTANCE.toDTOWithSites(i,sites);
            ret.add(s);
        }
        return ret;
    }

    public List<BuildingDTO> toDTO(List<BuildingEntity> entities){
        return entities.stream().map(BuildingMapper.INSTANCE::toDTO).toList();
    }

}
