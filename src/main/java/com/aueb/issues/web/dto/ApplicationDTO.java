package com.aueb.issues.web.dto;

import com.aueb.issues.model.entity.SiteEntity;
import com.aueb.issues.model.enums.Priority;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * This class will transfer data to committee and Technitian for viewing applications
 */
@Getter
@Setter
public class ApplicationDTO {

    private String id;
    private String title;
    private String siteName;
    private String status;
    private String buildingName;
    private String priority;

    private String description;
    private String issueType;
    private LocalDateTime dueDate;
}
