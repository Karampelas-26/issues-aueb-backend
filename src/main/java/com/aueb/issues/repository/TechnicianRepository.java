package com.aueb.issues.repository;

import com.aueb.issues.model.entity.ApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TechnicianRepository extends JpaRepository<ApplicationEntity, String> {

}
