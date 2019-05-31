package com.recruiting.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@ToString(exclude = {"resumes","vacancies"})
@EqualsAndHashCode(of = { "id" })
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    private boolean active;

    private String password;

    private String email;


    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @JsonIgnore
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Resume> resumes;

    @JsonIgnore
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Vacancy> vacancies;

    private String busyness;

    public User() {
    }

    public User(@NotBlank(message = "Username cannot be empty") String username, @NotBlank(message = "Password cannot be empty") String password, @Email(message = "Email is not correct") @NotBlank(message = "Email cannot be empty") String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }


}
