package com.aueb.issues.repository;

import com.aueb.issues.model.entity.BuildingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BuildingRepository extends JpaRepository<BuildingEntity, Integer> {

    public default Optional<BuildingEntity>getBuildingById(Long id){
        return findById(Math.toIntExact(id));
    }

    Optional<BuildingEntity> findByName(String name);


}
