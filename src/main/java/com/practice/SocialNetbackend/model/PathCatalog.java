package com.practice.SocialNetbackend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "path_catalog")
public class PathCatalog {

    public PathCatalog(String pathName, Storage storage) {
        this.pathName = pathName;
        this.storage = storage;
    }

    public PathCatalog(String pathName, Storage storage, PathCatalog parent) {
        this.pathName = pathName;
        this.storage = storage;
        this.parent = parent;
    }

    public PathCatalog(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotNull
    @NotBlank
    @Column(name = "path")
    private String pathName;

    @OneToOne(mappedBy = "pathCatalogRoot", fetch = FetchType.LAZY)
    private Storage storage;

    @OneToMany(mappedBy = "pathCatalog", fetch = FetchType.LAZY)
    private List<File> files;

    @OneToMany(mappedBy = "parent", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PathCatalog> pathCatalogs;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private PathCatalog parent;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PathCatalog that = (PathCatalog) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
