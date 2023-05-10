package com.prictice.socialnet.dto;

import com.prictice.socialnet.domain.Comment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {
    private Long id;
    private String message;
    private Long person;
    private Long news;

    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.message = comment.getMessage();
        this.person = comment.getPersonProfile().getId();
        this.news = comment.getNews().getId();
    }
}
