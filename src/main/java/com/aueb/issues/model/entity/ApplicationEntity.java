package com.aueb.issues.model.entity;

import com.aueb.issues.model.enums.IssueType;
import com.aueb.issues.model.enums.Priority;
import com.aueb.issues.model.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
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


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public SiteEntity getSite() {
        return site;
    }

    public void setSite(SiteEntity site) {
        this.site = site;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDateTime completionDate) {
        this.completionDate = completionDate;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public IssueType getIssueType() {
        return issueType;
    }

    public void setIssueType(IssueType issueType) {
        this.issueType = issueType;
    }

    public UserEntity getCreationUser() {
        return creationUser;
    }

    public void setCreationUser(UserEntity creationUser) {
        this.creationUser = creationUser;
    }

    public UserEntity getAssigneeTech() {
        return assigneeTech;
    }

    public void setAssigneeTech(UserEntity assigneeTech) {
        this.assigneeTech = assigneeTech;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
