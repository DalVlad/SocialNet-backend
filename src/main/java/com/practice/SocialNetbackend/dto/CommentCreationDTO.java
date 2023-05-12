package com.practice.SocialNetbackend.dto;


import com.practice.SocialNetbackend.model.Person;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class CommentCreationDTO {
    CommentOnPublicationDTO commentOnPublicationDTO;
    Person person;
}
