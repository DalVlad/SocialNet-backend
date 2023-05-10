package com.prictice.socialnet.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class NewsLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "person_profile_id",
            foreignKey = @ForeignKey(name = "person_likes_id_fk")
    )
    private PersonProfile personProfile;
    @ManyToOne
    @JoinColumn(name = "news_id",
            foreignKey = @ForeignKey(name = "news_likes_id_fk")
    )
    private News news;
}
