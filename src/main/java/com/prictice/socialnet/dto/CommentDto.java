package com.prictice.socialnet.domain.dto;

import com.prictice.socialnet.domain.Comment;
import lombok.Getter;

@Getter
public class CommentDto {
    private final Long id;
    private final String message;
    private final String firstName;
    private final String lastName;

    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.message = comment.getMessage();
        this.firstName = comment.getPersonProfile().getFirstName();
        this.lastName = comment.getPersonProfile().getLastName();
    }
}
