package com.aueb.issues.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentDTO {
    private String typeOfEquipment;
    private int buildingId;
    private String siteId;
}
