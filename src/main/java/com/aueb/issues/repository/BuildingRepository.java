package com.aueb.issues.repository;

import com.aueb.issues.model.entity.BuildingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingRepository extends JpaRepository<BuildingEntity, Integer> {
}
