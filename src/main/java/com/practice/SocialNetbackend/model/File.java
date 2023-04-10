package com.practice.SocialNetbackend.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "file")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;

    @NotNull
    @NotBlank
    @Column(name = "extension")
    private String extension;

    @Column(name = "name")
    private String name;

    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    @Column(name = "file_byte")
    private byte[] file;

    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    @Column(name = "preview")
    private byte[] preview;

    @ManyToOne
    @JoinColumn(name = "path_catalog_id", referencedColumnName = "id")
    private PathCatalog pathCatalog;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        File file = (File) o;
        return id == file.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
