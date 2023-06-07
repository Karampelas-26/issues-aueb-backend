package com.aueb.issues.web.service;

import com.aueb.issues.model.entity.BuildingEntity;
import com.aueb.issues.model.entity.SiteEntity;
import com.aueb.issues.model.mapper.SiteMapper;
import com.aueb.issues.repository.BuildingRepository;
import com.aueb.issues.repository.SitesRepository;
import com.aueb.issues.web.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class SiteService {
    @Autowired
    SitesRepository sitesRepository;
    @Autowired
    BuildingRepository buildingRepository;

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
}
