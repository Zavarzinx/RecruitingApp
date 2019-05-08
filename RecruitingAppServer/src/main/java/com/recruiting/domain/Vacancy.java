package com.recruiting.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "vacancies")
@Data
@ToString
@EqualsAndHashCode(of = { "id" })
public class Vacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String title;
    String text;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    User author;

    private String email;

    private String phone;

    private LocalDateTime createdAt;

    private LocalDateTime lastUpdated;

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
    public void removeVacancyFromUser(){
        author.getVacancies().remove(this);
    }
}
