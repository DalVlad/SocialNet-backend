package com.prictice.socialnet.dto;

import com.prictice.socialnet.domain.NewsLike;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewsLikeDto {
    private Long id;
    private Long person;
    private Long news;

    public NewsLikeDto(NewsLike newsLike) {
        this.id = newsLike.getId();
        this.person = newsLike.getPersonProfile().getId();
        this.news = newsLike.getNews().getId();
    }
}
