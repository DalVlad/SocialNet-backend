package com.practice.SocialNetbackend.dto;


import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class CommunityDTO {

    private Long id;

    @NotNull
    private String name; //Имя пути

    @NotNull
    private String title;       //Само название паблика

    private String description;

}
