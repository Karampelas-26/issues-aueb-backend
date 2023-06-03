package com.aueb.issues.repository;

import com.aueb.issues.model.entity.SiteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SitesRepository extends JpaRepository<SiteEntity, String> {
    public default Optional<SiteEntity> findSiteById(String id){
        return findById(id);
    }
    @Query(value = "select s from SiteEntity as s"+
    " where (:name = null or s.name=:name)")
    public List<SiteEntity> findByName(@Param("name")String name);
//    @Query(value = "select s from SiteEntity as s"+
//    "where(:buildingId=null or s.building.id=:buildingId)")
//    public List<SiteEntity> getSitesOfBuilding(@Param("buildingId")Long buildingId);
}
