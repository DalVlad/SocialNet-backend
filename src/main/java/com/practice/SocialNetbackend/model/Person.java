package com.practice.SocialNetbackend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;


    @Size(min = 3, max = 255, message = "login должен быть от 3 до 255 символов")
    @NotBlank
    @NotNull
    @Column(name = "login")
    private String login;

    @NotBlank
    @NotNull
    @Column(name = "password")
    private String password;



    @Column(name = "user_name")
    private String user_name;

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY)
    private List<Member> members;

    @OneToMany(mappedBy = "person")
    private List<CommentOnPublication> commentOnPublications;




    @OneToMany(mappedBy = "person")
    private List<Storage> storages;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id && Objects.equals(login, person.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login);
    }
}
