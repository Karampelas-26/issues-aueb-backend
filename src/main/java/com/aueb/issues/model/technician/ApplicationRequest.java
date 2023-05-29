package com.aueb.issues.model.technician;

import com.aueb.issues.model.entity.BuildingEntity;
import com.aueb.issues.model.entity.Priority;
import com.aueb.issues.model.entity.SitesEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationRequest {
   /* private String id;*/
    private String title;
    private SitesEntity sites;
    private BuildingEntity building;
    private Priority priority;
    private LocalDateTime createDate;
    private LocalDateTime completionDate;
    private boolean complete;
}
