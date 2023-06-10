package com.aueb.issues.web.dto;
import com.aueb.issues.model.entity.SiteEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BuildingDTO {
    private Long id;
    private String name;
    private String address;
    private int floors;
    private List<SiteDTO> sites;
}
