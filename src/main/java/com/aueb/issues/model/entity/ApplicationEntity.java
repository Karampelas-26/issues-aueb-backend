package com.aueb.issues.model.entity;

import com.aueb.issues.model.enums.IssueType;
import com.aueb.issues.model.enums.Priority;
import com.aueb.issues.model.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @author George Karampelas
 * @author Meme-iDanis
 */
//@Data
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
    @Enumerated(EnumType.STRING)
    private IssueType issueType;
    @ManyToOne
    @JoinColumn(name = "creation_user_id")
    private UserEntity creationUser;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "application_id")
    private List<CommentEntity> comments;

    @ManyToOne
    @JoinColumn(name = "assignee_tech_id")
    private UserEntity assigneeTech;
    @Enumerated(EnumType.STRING)
    private Status status;
}
