package com.aueb.issues.model.entity;

import com.aueb.issues.model.enums.Priority;
import com.aueb.issues.model.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author George Karampelas
 * @author Meme-iDanis
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "application")
public class ApplicationEntity {


    @Id
    private String id;
    private String title;
    @OneToOne
    @JoinColumn(name = "sites_entity_id")
    private SitesEntity sites;
    @OneToOne
    @JoinColumn(name = "building_id")
    private BuildingEntity building;
    @Enumerated(EnumType.STRING)
    private Priority priority;
    private String description;
    private LocalDateTime createDate;
    private LocalDateTime completionDate;
    private String creationUserId;
    private String assigneeTechId;
    @Enumerated(EnumType.STRING)
    private Status status;
    //create comments

}
