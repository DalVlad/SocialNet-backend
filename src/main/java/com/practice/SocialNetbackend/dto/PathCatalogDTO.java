package com.practice.SocialNetbackend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PathCatalogDTO {

    private String pathName;

    private List<FileDTO> files;

}
