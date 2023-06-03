package com.aueb.issues.model.entity;

import com.aueb.issues.model.enums.IssueType;
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
    @JoinColumn(name="site_id")
    private SiteEntity site;

    @Enumerated(EnumType.STRING)
    private Priority priority;
    private String description;
    private LocalDateTime createDate;
    private LocalDateTime completionDate;
    private LocalDateTime dueDate;
    private IssueType issueType;
    @ManyToOne
    @JoinColumn(name = "creation_user_id")
    private UserEntity creationUser;
    @ManyToOne
    @JoinColumn(name = "assignee_tech_id")
    private UserEntity assigneeTech;
    @Enumerated(EnumType.STRING)
    private Status status;

    //create comments

}
