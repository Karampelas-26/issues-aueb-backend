package com.aueb.issues.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author George Karampelas
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "site")
public class SiteEntity {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String name;
    private String floor;

    @ManyToOne
    @JoinColumn(name="buildingId")
    private BuildingEntity building;

    @ManyToMany
    private List<EquipmentEntity> equipmentEntities;

    public void addEquipment(EquipmentEntity equipment) {
        if (equipmentEntities == null)
            equipmentEntities = new ArrayList<>();
        if (!equipmentEntities.contains(equipment)) {
            equipmentEntities.add(equipment);
        }
    }

    public void deleteEquipment(EquipmentEntity equipment) {
        if (equipmentEntities != null) {
            equipmentEntities.remove(equipment);
        }
    }
}
