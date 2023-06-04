package com.aueb.issues.repository;

import com.aueb.issues.model.entity.BuildingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BuildingRepository extends JpaRepository<BuildingEntity, Integer> {

    public default Optional<BuildingEntity>getBuildingById(Long id){
        return findById(Math.toIntExact(id));
    }
}
