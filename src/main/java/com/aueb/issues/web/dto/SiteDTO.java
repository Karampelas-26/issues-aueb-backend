package com.aueb.issues.web.dto;

import com.aueb.issues.model.entity.BuildingEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SiteDTO {
    private Long id;
    private String name;
    private String floor;
    private BuildingDTO building;
}
