package com.aueb.issues.model.services;

import com.aueb.issues.model.entity.SiteEntity;
import com.aueb.issues.repository.SitesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SiteService {

    @Autowired
    SitesRepository sitesRepository;

    public SiteEntity getSiteBySiteId(String id){
        return sitesRepository.getReferenceById(id);
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
}
