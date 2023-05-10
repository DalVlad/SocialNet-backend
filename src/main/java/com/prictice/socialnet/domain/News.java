package com.prictice.socialnet.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
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

    @ManyToOne
    @JoinColumn(name = "person_profile_id",
            foreignKey = @ForeignKey(name = "person_news_id_fk")
    )
    private PersonProfile personProfile;

    @OneToMany
    private Set<NewsLike> likes = new HashSet<>();
}
