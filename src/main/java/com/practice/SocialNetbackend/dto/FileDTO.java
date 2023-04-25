package com.practice.SocialNetbackend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileDTO {

    private long id;

    private String name;

    private String extension;

    private byte[] preview;

}
