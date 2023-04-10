package com.practice.SocialNetbackend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileDTO {

    private String name;

    private String extension;

    private byte[] preview;

}
