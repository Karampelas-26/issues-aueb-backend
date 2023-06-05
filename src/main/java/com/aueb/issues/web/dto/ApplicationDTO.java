package com.aueb.issues.web.dto;

import com.aueb.issues.model.entity.SiteEntity;
import com.aueb.issues.model.enums.Priority;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

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
    private LocalDateTime createDate;
    private String assigneeTechName;
    private List<CommentDTO> comments;
    private String description;
    private String issueType;
    private LocalDateTime dueDate;
}
