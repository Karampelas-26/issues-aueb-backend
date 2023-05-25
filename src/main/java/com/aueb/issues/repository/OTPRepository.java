package com.aueb.issues.repository;

import com.aueb.issues.model.entity.OTP;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface OTPRepository extends JpaRepository<OTP, String> {
    void deleteByUserId(String userId);
    Optional<OTP> findByUserId(String userId);
}