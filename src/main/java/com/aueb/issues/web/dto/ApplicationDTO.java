package com.aueb.issues.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationDTO {
    private String buildingId;
    private String siteId;
    private String issueType;
    private String equipmentId;
    private String description;
}
