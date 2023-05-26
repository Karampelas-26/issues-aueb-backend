package com.aueb.issues.repository;

import com.aueb.issues.model.entity.SitesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SitesRepository extends JpaRepository<SitesEntity, String> {
}
