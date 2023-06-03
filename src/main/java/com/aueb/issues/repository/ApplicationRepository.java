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
            "where (:site_name = null or a.site.name = :site_name)  "+
            "and (:priority = null or a.priority =:priority)  "+
            "and (:issue_type = null or a.issueType =:issue_type)  "+
            "and (:status = null or a.status =:status)  ")
    public List<ApplicationEntity> findByValues(@Param("site_name")String siteName,
                                                  @Param("priority")String priority,
                                                  @Param("issue_type")String issueType,
                                                  @Param("status")String status);


    @Query("SELECT a FROM ApplicationEntity a JOIN a.site s JOIN s.building b WHERE b.name = :buildingName")
    List<ApplicationEntity> findByBuildingName(@Param("buildingName") String buildingName);
}
