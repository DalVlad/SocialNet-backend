package com.practice.SocialNetbackend.model;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="member_role")
public class MemberRole {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "role")
    private String role;

    @OneToMany(mappedBy = "memberRole")
    private List<Publication> publications;

    @OneToMany(mappedBy = "memberRole")
    private List<Member> members;
}
