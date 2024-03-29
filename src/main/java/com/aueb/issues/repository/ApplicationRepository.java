package com.aueb.issues.repository;

import com.aueb.issues.model.entity.ApplicationEntity;
import com.aueb.issues.model.entity.UserEntity;
import com.aueb.issues.model.enums.IssueType;
import com.aueb.issues.model.enums.Priority;
import com.aueb.issues.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
public interface ApplicationRepository extends JpaRepository<ApplicationEntity, String> {
    @Query(value = "select a from ApplicationEntity as a " +
            "JOIN a.site as s JOIN s.building b " +
            "where (:site_name = null or a.site.name = :site_name)  "+
            "and (:priority = null or a.priority =:priority)  "+
            "and (:issue_type = null or a.issueType =:issue_type)  "+
            "and (:status = null or a.status =:status)   "+
            "and (:buildingName = null or b.name=:buildingName) ")
    public List<ApplicationEntity> findByValues(@Param("site_name")String siteName,
                                                @Param("priority") Priority priority,
                                                @Param("issue_type") IssueType issueType,
                                                @Param("status") Status status,
                                                @Param("buildingName")String buildingName);


    @Query("SELECT a FROM ApplicationEntity a JOIN a.creationUser WHERE a.assigneeTech = :assignee")
    List<ApplicationEntity> findByUser(@Param("assignee")UserEntity user);

    //statistics querries
    @Query(value = "SELECT ((CAST(EXTRACT(YEAR FROM appl.create_date) AS TEXT)) || '-' || (CAST(EXTRACT(MONTH FROM appl.create_date) AS TEXT)) )AS MonthNumber, count(*)\n, count (*) " +
            "FROM public.application appl " +
            "join public.site on appl.site_id =site.id " +
            "join public.building on site.building_id=building.id "+
            "WHERE appl.create_date >= COALESCE(:startDate, appl.create_date) " +
            "AND appl.create_date <= COALESCE(:endDate, appl.create_date) " +
            "AND appl.issue_type = COALESCE(:issueType, appl.issue_type)"+
            "And building.id = COALESCE(:buildingId, building.id) "+
            "GROUP BY MonthNumber", nativeQuery = true)
    List<Map<String, Object>> countApplicationsByMonthWithFilters(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("buildingId")Long buildingId,
            @Param("issueType")String issueType
    );
}
