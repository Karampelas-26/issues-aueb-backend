package com.aueb.issues.repository;

import com.aueb.issues.model.entity.ActivationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActivationTokenRepository extends JpaRepository<ActivationToken, String> {
    Optional<ActivationToken> findByActivationToken(String id);
}