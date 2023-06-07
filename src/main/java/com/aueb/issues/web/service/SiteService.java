package com.aueb.issues.web.service;

import com.aueb.issues.model.entity.BuildingEntity;
import com.aueb.issues.model.entity.SiteEntity;
import com.aueb.issues.model.mapper.SiteMapper;
import com.aueb.issues.repository.BuildingRepository;
import com.aueb.issues.repository.SitesRepository;
import com.aueb.issues.web.dto.BuildingDTO;
import com.aueb.issues.web.dto.SiteDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


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

    public ResponseEntity<String> createSites(SiteDTO requestDTO) {
        try{
                String buildingName = requestDTO.getBuilding().getName();
                String siteName = requestDTO.getName();
                List<BuildingEntity> bui = buildingRepository.findByName(buildingName);
                for(BuildingEntity b :bui){
                    if(!buildingName.equals(b.getName())){
                        BuildingEntity buildingEntity = BuildingEntity.builder()
                                .name(buildingName)
                                .address(requestDTO.getFloor())
                                .floors(Integer.parseInt(requestDTO.getFloor()))
                                .build();
                        buildingRepository.save(buildingEntity);
                        SiteEntity siteEntity = SiteEntity.builder()
                                .name(requestDTO.getName())
                                .floor(requestDTO.getFloor())
                                .building(buildingEntity)
                                .build();
                        sitesRepository.save(siteEntity);

                    }
                    else {
                        List<SiteEntity> sites = sitesRepository.findByName(siteName);
                        for(SiteEntity s:sites){
                            if(!s.getName().equals(siteName)){
                                BuildingEntity building = BuildingEntity.builder()
                                        .name(buildingName)
                                        .address(requestDTO.getFloor())
                                        .floors(Integer.parseInt(requestDTO.getFloor()))
                                        .build();
                                SiteEntity siteEntity = SiteEntity.builder()
                                        .name(requestDTO.getName())
                                        .floor(requestDTO.getFloor())
                                        .building(building)
                                        .build();
                                sitesRepository.save(siteEntity);
                            }
                        }
                    }
                }





        }catch (Exception e){
            log.error(e.toString());
            return null;
        }
        return ResponseEntity.ok(null);
    }
}
