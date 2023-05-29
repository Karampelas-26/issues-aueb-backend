package com.aueb.issues.model.services;

import com.aueb.issues.model.entity.BuildingEntity;
import com.aueb.issues.repository.BuildingRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuildingService {

    @Autowired
    BuildingRepository buildingRepository;

    public BuildingEntity getBuildingById(Integer id){
        return buildingRepository.getReferenceById(id);
    }
}
