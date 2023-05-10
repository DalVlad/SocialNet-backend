package com.prictice.socialnet.dto;

import com.prictice.socialnet.domain.News;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NewsDto {
    private Long id;
    private String text;
    private String picture;
    private Long person;

    public NewsDto(News news) {
        this.id = news.getId();
        this.text = news.getText();
        this.picture = news.getPicture();
        this.person = news.getPersonProfile().getId();
    }
}
