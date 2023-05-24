package com.aueb.issues.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.aueb.issues.model.entity.BuildingEntity;

/**
 * @author George Karampelas
 * @author Meme-iDanis
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "application")
public class ApplicationEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private String id;
    private String title;
    @OneToOne
    @JoinColumn(name = "sites_entity_id")
    private SitesEntity sites;
    @OneToOne
    @JoinColumn(name = "building_id")
    private BuildingEntity building;
}
