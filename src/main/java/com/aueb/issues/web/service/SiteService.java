package com.aueb.issues.web.service;

import com.aueb.issues.model.entity.SiteEntity;
import com.aueb.issues.model.mapper.SiteMapper;
import com.aueb.issues.repository.SitesRepository;
import com.aueb.issues.web.dto.SiteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SiteService {
    @Autowired
    SitesRepository sitesRepository;

    public SiteEntity getSiteBySiteId(String id){
        return sitesRepository.getReferenceById(id);
    }

    public ResponseEntity<List<SiteDTO>> getAllSites(){
        return ResponseEntity.ok(toDto(sitesRepository.findAll()));
    }

    public List<SiteDTO> toDto(List<SiteEntity> entities){
        return entities.stream().map(SiteMapper.INSTANCE::toDTO).toList();
    }
}
