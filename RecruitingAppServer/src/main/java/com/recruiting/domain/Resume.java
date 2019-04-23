package com.recruiting.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "resumes")
@ToString(of = {"id", "text","author"})
@EqualsAndHashCode(of = {"id"})
@Data
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String text;

    private String author;

    public Resume() {
    }

    public Resume(String text, String author) {
        this.text = text;
        this.author = author;
    }
}
