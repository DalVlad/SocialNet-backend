package com.prictice.socialnet.domain.dto;

import com.prictice.socialnet.domain.News;
import lombok.Getter;

@Getter
public class NewsDto {
    private final Long id;
    private final String text;
    private final String picture;
    private final String firstName;
    private final String lastName;

    public NewsDto(News news){
        this.id = news.getId();
        this.text = news.getText();
        this.picture = news.getPicture();
        this.firstName = news.getPersonProfile().getFirstName();
        this.lastName = news.getPersonProfile().getLastName();
    }
}
