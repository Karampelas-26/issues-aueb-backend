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
        equipmentEntities.add(equipment);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public BuildingEntity getBuilding() {
        return building;
    }

    public void setBuilding(BuildingEntity building) {
        this.building = building;
    }

    public List<EquipmentEntity> getEquipmentEntities() {
        return equipmentEntities;
    }

    public void setEquipmentEntities(List<EquipmentEntity> equipmentEntities) {
        this.equipmentEntities = equipmentEntities;
    }
}
