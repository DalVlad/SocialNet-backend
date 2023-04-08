package com.prictice.socialnet.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "person_comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "comment_text")
    private String message;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_profile_id",
            foreignKey = @ForeignKey(name = "person_comment_id_fk")
    )
    private PersonProfile personProfile;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "news_id",
            foreignKey = @ForeignKey(name = "news_comment_id_fk")
    )
    private News news;
}
