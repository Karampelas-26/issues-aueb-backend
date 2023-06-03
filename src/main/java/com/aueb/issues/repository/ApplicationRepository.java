package com.aueb.issues.repository;

import com.aueb.issues.model.entity.ApplicationEntity;
import com.aueb.issues.model.entity.BuildingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<ApplicationEntity, String> {
    @Query(value = "select a from ApplicationEntity as a " +
            "where (:site_name = null or a.site.name = :site_name)  ")
    public List<ApplicationEntity> findBySiteName(@Param("site_name")String siteName);
//    @Query("SELECT a FROM ApplicationEntity a WHERE a.building.id = :buildingId")

    @Query("SELECT a FROM ApplicationEntity a JOIN a.site s JOIN s.building b WHERE b.name = :buildingName")
    List<ApplicationEntity> findByBuildingName(@Param("buildingName") String buildingName);
}
