package com.aueb.issues.repository;

import com.aueb.issues.model.entity.SiteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SitesRepository extends JpaRepository<SiteEntity, String> {
}
