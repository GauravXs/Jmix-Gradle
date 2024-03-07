package com.company.projectmanagement.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.annotation.JmixProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@JmixEntity
@Table(name = "BUG_ERROR", indexes = {
        @Index(name = "IDX_BUG_ERROR_DEVELOPER_ASSIGNED", columnList = "DEVELOPER_ASSIGNED_ID"),
        @Index(name = "IDX_BUG_ERROR_PROJECT_NAME", columnList = "PROJECT_NAME_ID")
})
@Entity
public class BugError {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "STATUS")
    private String status;

    @InstanceName
    @Column(name = "TITLE", nullable = false)
    @NotNull
    private String title;

    @Column(name = "DESCRIPTION")
    @Lob
    private String description;

    @NotNull
    @JoinColumn(name = "DEVELOPER_ASSIGNED_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User developerAssigned;

    @JoinColumn(name = "PROJECT_NAME_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Project projectName;

    @Column(name = "VERSION", nullable = false)
    @Version
    private Integer version;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Project getProjectName() {
        return projectName;
    }

    public void setProjectName(Project projectName) {
        this.projectName = projectName;
    }

    public User getDeveloperAssigned() {
        return developerAssigned;
    }

    public void setDeveloperAssigned(User developerAssigned) {
        this.developerAssigned = developerAssigned;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}