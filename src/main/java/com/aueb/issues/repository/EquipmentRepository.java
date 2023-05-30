package com.aueb.issues.repository;

import com.aueb.issues.model.entity.EquipmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentRepository extends JpaRepository<EquipmentEntity, String> {
}
