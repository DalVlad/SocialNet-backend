package com.practice.SocialNetbackend.dto;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class PublicationDTO {

    private Long id;
    private String member_role;
    private Date createdAt;
    private String message;
    private String community_name;
}
