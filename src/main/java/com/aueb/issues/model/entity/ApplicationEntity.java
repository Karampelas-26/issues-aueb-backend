package com.aueb.issues.model.entity;

import com.aueb.issues.model.enums.Priority;
import com.aueb.issues.model.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

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
    private String id;
    private String title;

    @ManyToOne
    private SiteEntity site;

//    @ManyToOne
//    private EquipmentEntity equipment;

    private Long building_id;
    @Enumerated(EnumType.STRING)
    private Priority priority;
    private String description;
    private LocalDateTime createDate;
    private LocalDateTime completionDate;
    private LocalDateTime dueDate;
    @ManyToOne
    private UserEntity creationUserId;
    private String assigneeTechId;
    @Enumerated(EnumType.STRING)
    private Status status;

    //create comments

}
