package com.aueb.issues.web.dto;

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
    private Priority priority;
    private int buildingId;
    private String siteId;
    private String issueType;
    private String equipmentId;
    private String description;
    private LocalDateTime dueDate;
    //todo: fill out the fields
}
