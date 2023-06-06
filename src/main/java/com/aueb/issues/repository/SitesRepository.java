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
}
