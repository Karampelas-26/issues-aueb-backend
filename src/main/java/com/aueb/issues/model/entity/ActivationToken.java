package com.aueb.issues.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author George Karampelas
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "activation_token")
public class ActivationToken {
    @Id
    private String userId;
    private String activationToken;
    private LocalDateTime tokenCreationDateTime;
}
