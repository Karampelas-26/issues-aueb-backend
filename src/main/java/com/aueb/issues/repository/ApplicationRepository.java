package com.aueb.issues.repository;

import com.aueb.issues.model.entity.ApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<ApplicationEntity, String> {

}
