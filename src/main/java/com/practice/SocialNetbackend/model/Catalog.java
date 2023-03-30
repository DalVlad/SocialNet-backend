package com.practice.SocialNetbackend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "catalog")
public class Catalog {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "catalog", cascade = CascadeType.ALL)
    private List<PathCatalog> pathCatalogs;

    @OneToMany(mappedBy = "catalog")
    private List<File> files;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Catalog catalog = (Catalog) o;
        return id == catalog.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
