package com.practice.SocialNetbackend.dto;

import lombok.Data;
@Data
public class CommentOnPublicationDTO {

    private Long id;
    private Long publication_id;
    private Long person_id;
    private String textOfComment;

}
