package com.aueb.issues.model.entity;

import jakarta.persistence.*;
import lombok.*;

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
@Table(name = "building")
public class BuildingEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String address;
    private int floors;
}
