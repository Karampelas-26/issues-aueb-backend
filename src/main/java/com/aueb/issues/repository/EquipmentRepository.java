package com.aueb.issues.repository;

import com.aueb.issues.model.entity.EquipmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EquipmentRepository extends JpaRepository<EquipmentEntity, Long> {
    @Query(value = "select e from EquipmentEntity as e"+
    " where(:siteId=null or e.site.id=:siteId)")
    public List<EquipmentEntity> getEquipmentOfSite(@Param("siteId")Long siteId);

    List<EquipmentEntity> findByTypeOfEquipment(String typeOfEquipment);

    Optional<EquipmentEntity> findEquipmentEntityById(Long id);
}
