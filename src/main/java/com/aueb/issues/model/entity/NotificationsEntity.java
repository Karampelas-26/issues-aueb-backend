package com.aueb.issues.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author George Karampelas
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(NotificationId.class)
@Table(name = "notification")
public class NotificationsEntity {

    @Id
    private String userId;
    @Id
    private LocalDateTime dateNotification;

    private String issueId;

}
