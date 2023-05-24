package com.aueb.issues.web;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubmitApplicationDTO {
    private String buildingId;
    private String siteId;
    private String issueType;
    private String equipmentId;
    private String description;
}
