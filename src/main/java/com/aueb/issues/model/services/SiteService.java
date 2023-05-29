package com.aueb.issues.model.services;

import com.aueb.issues.model.entity.SiteEntity;
import com.aueb.issues.repository.SitesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SiteService {
    @Autowired
    SitesRepository sitesRepository;

    public SiteEntity getSiteBySiteId(String id){
        return sitesRepository.getReferenceById(id);
    }
}
