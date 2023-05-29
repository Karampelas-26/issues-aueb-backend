package com.aueb.issues.web.dto;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Getter
@Setter
public class NotificationDTO {
    private String userId;
    private LocalDateTime dateNotification;
    private String issueId;
}
