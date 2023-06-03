package com.aueb.issues.repository;

import com.aueb.issues.model.entity.ActivationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Repository
public interface ActivationTokenRepository extends JpaRepository<ActivationToken, String> {
    Optional<ActivationToken> findByActivationToken(String id);

    Optional<ActivationToken> findByUserId(String email);
}