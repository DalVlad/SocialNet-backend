package com.prictice.socialnet.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "person_comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "comment_text")
    private String message;

    @ManyToOne
    @JoinColumn(name = "person_profile_id",
            foreignKey = @ForeignKey(name = "person_comment_id_fk")
    )
    private PersonProfile personProfile;

    @ManyToOne
    @JoinColumn(name = "news_id",
            foreignKey = @ForeignKey(name = "news_comment_id_fk")
    )
    private News news;
}
