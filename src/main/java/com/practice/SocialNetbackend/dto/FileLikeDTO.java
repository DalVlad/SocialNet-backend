package com.practice.SocialNetbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FileLikeDTO {

    private long fileId;

    private long likes;

    private boolean like_person;

}
