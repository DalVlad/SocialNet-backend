package com.prictice.socialnet.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "person_news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "news_text")
    private String text;
    @Column(name = "picture")
    private String picture;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_profile_id",
            foreignKey = @ForeignKey(name = "person_news_id_fk")
    )
    private PersonProfile personProfile;

    @OneToMany(mappedBy = "news",
            orphanRemoval = true
    )
    private Set<Comment> comments;
}
