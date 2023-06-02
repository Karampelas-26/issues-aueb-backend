package com.aueb.issues.repository;

import com.aueb.issues.model.entity.SiteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SitesRepository extends JpaRepository<SiteEntity, String> {
}
