package com.practice.SocialNetbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class PublicationDTO {

    private Long id;
    private String member_role;
    private Date createdAt;
    private String message;
    private String community_name;
}
