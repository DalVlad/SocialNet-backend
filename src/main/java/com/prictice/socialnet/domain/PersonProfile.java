package com.prictice.socialnet.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "person_profile")
public class PersonProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "email")
    private String email;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "birthdate")
    private LocalDate birthdate;
    @Column(name = "person_id")
    private Long person;

    @OneToMany(mappedBy = "personProfile",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<News> personNews;

    @OneToMany(mappedBy = "personProfile",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<Comment> personComment;
}
