package com.aueb.issues.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "equipment")
public class EquipmentEntity {
    @Id
    private String id;
    @OneToOne
    @JoinColumn(name = "building_id")
    private BuildingEntity building;
    @OneToOne
    @JoinColumn(name = "sites_entity_id")
    private SiteEntity site;
    private String typeOfEquipment;
    private LocalDateTime installationDate;
}
