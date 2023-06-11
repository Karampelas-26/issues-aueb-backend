package com.aueb.issues.web.service;

import com.aueb.issues.model.entity.BuildingEntity;
import com.aueb.issues.model.entity.EquipmentEntity;
import com.aueb.issues.model.entity.SiteEntity;
import com.aueb.issues.model.mapper.SiteMapper;
import com.aueb.issues.repository.BuildingRepository;
import com.aueb.issues.repository.EquipmentRepository;
import com.aueb.issues.repository.SitesRepository;
import com.aueb.issues.web.dto.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
@Slf4j
public class SiteService {
    @Autowired
    SitesRepository sitesRepository;
    @Autowired
    BuildingRepository buildingRepository;
    @Autowired
    EquipmentRepository equipmentRepository;

    public SiteEntity getSiteBySiteId(String id){
        return sitesRepository.getReferenceById(id);
    }

    public ResponseEntity<List<SiteDTO>> getAllSites(){
        return ResponseEntity.ok(toDto(sitesRepository.findAll()));
    }

    public List<SiteDTO> toDto(List<SiteEntity> entities){
        return entities.stream().map(SiteMapper.INSTANCE::toDTO).toList();
    }

    public ResponseEntity<ResponseMessageDTO> createSites(CreateBuildingRequest request) {
        try{
            Optional<BuildingEntity> isBuilding = buildingRepository.findByName(request.getName());
            if(isBuilding.isPresent()){
                return ResponseEntity.badRequest().body(new ResponseMessageDTO("Building with name: " + request.getName() + " already exists"));
            }
            BuildingEntity building = BuildingEntity.builder()
                    .name(request.getName())
                    .address(request.getAddress())
                    .floors(request.getFloors())
                    .build();
            buildingRepository.save(building);
            for(CreateSiteRequest site: request.getSites()){
                SiteEntity siteEntity = SiteEntity.builder()
                        .name(site.getName())
                        .floor(site.getFloor())
                        .building(building)
                        .build();
                sitesRepository.save(siteEntity);
            }
        }catch (Exception e){
            log.error(e.toString());
            return null;
        }
        return ResponseEntity.ok(null);
    }

    public ResponseEntity<List<String>> getAllSitesName(){
        List<SiteEntity> sites = sitesRepository.findAll();
        if(sites.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        List<String> names = sites.stream()
                .map(SiteEntity::getName)
                .toList();

        return ResponseEntity.ok(names);
    }

    public ResponseEntity<Map<String, List<String>>> getSiteByBuildingAndEquipmentType(String typeOfEquipment) {
        List<BuildingEntity> buildings = buildingRepository.findAll();
        List<EquipmentEntity> equipment = equipmentRepository.findByTypeOfEquipment(typeOfEquipment);

        Map<String, List<String>> buildingSitesMap = new HashMap<>();

        for (BuildingEntity building : buildings) {
            List<SiteEntity> buildingSites = sitesRepository.findByBuildingId(building.getId());

            List<String> filteredSites = buildingSites.stream()
                    .filter(site -> site.getEquipmentEntities().stream()
                            .anyMatch(siteEquipment -> equipment.contains(siteEquipment)))
                    .map(SiteEntity::getName)
                    .collect(Collectors.toList());

            if (!filteredSites.isEmpty()) {
                buildingSitesMap.put(building.getName(), filteredSites);
            }
        }

        return ResponseEntity.ok(buildingSitesMap);
    }
    //for submit
    public ResponseEntity<List<String>> getEquipmentOfSite(String siteName){
        SiteEntity site = sitesRepository.findByName(siteName);
        List<String> ret=site.getEquipmentEntities().stream().map(eq->eq.getTypeOfEquipment()).toList();
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }
    private boolean siteContainsEquipmentType(SiteEntity site, String typeOfEquipment) {
        List<EquipmentEntity> equipmentEntities = site.getEquipmentEntities();

        for (EquipmentEntity equipment : equipmentEntities) {
            if (equipment.getTypeOfEquipment().equals(typeOfEquipment)) {
                return true;
            }
        }

        return false;
    }

    public ResponseEntity<ResponseMessageDTO> deleteEquipmentsOnSites(Long typeOfEquipment, List<String> sitesName) {
        try{
            EquipmentEntity equipment = equipmentRepository.findEquipmentEntityById(typeOfEquipment).orElseThrow(()->new EntityNotFoundException("Equipment not found"));
            List<SiteEntity> existingSites = sitesRepository.findAllByNameIn(sitesName);
            for (SiteEntity site : existingSites) {
                if (site.getEquipmentEntities().contains(equipment)) {
                    site.deleteEquipment(equipment);
                    sitesRepository.save(site);
                }
            }
            return ResponseEntity.ok(new ResponseMessageDTO("Successfully deleted"));
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(new ResponseMessageDTO(e.getMessage()));
        }
    }

    public ResponseEntity<ResponseMessageDTO> addEquipmentsOnSites(Long typeOfEquipment, List<String> sitesName) {
        try{
            EquipmentEntity equipment = equipmentRepository.findEquipmentEntityById(typeOfEquipment).orElseThrow(()->new EntityNotFoundException("Equipment not found"));
            List<SiteEntity> existingSites = sitesRepository.findAllByNameIn(sitesName);
            for (SiteEntity site : existingSites) {
                site.addEquipment(equipment);
                sitesRepository.save(site);
            }
            return ResponseEntity.ok(new ResponseMessageDTO("Successfully added"));
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(new ResponseMessageDTO(e.getMessage()));
        }
    }
}
