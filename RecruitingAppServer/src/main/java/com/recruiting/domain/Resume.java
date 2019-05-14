package com.recruiting.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "resumes")
@ToString
@EqualsAndHashCode(of = {"id"})
@Data
public class Resume {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Please fill the resume")
    private String text;

    private String email;

    private String phone;

    private String title;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    @Column(name = "creation_date")
    private LocalDateTime createdAt;

    @Column(name = "last_update")
    private LocalDateTime lastUpdated;

    public Resume() {
    }

    public Resume(String text, User author) {
        this.text = text;
        this.author = author;
    }

    @PrePersist
    protected void prePersist() {
        if (this.createdAt == null) createdAt = LocalDateTime.now();
        if (this.lastUpdated == null) lastUpdated = LocalDateTime.now();
    }

    @PreUpdate
    protected void preUpdate() {
        this.lastUpdated = LocalDateTime.now();
    }

    @PreRemove
    public void removeResumeFromUser(){
        author.getResumes().remove(this);
    }
}
