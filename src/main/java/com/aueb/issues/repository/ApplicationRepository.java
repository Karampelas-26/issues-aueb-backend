package com.aueb.issues.repository;

import com.aueb.issues.model.entity.ApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<ApplicationEntity, String> {
    @Query(value = "select a from ApplicationEntity as a " +
            "where (:building_id = null or a.building_id = :building_id)  ")
    public List<ApplicationEntity> findCustom(@Param("building_id") Long building_id);
//    @Query("SELECT a FROM ApplicationEntity a WHERE a.building.id = :buildingId")
}
