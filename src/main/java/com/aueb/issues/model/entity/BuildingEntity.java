package com.aueb.issues.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

/**
 * @author George Karampelas
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "building")
public class BuildingEntity {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String address;
    private int floors;
//    private

}
