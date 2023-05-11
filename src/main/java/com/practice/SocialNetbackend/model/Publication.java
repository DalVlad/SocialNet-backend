package com.practice.SocialNetbackend.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Publication")
public class Publication {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "own_community_id", referencedColumnName = "id")
    private Community ownCommunity;

    @ManyToOne
    @JoinColumn(name = "member_role_id", referencedColumnName = "id")
    private MemberRole memberRole;

    @OneToMany(mappedBy = "publication", cascade = CascadeType.REMOVE)
    private List<CommentOnPublication> commentOnPublications;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "message")
    private String message;

}
