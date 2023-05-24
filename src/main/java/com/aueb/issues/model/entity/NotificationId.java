package com.aueb.issues.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author George Karampelas
 */
public class NotificationId implements Serializable {
    private String userId;
    private LocalDateTime dateNotification;
}
