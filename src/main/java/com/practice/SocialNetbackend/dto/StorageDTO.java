package com.practice.SocialNetbackend.dto;

import com.practice.SocialNetbackend.model.File;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StorageDTO {

    private List<PathCatalogDTO> pathCatalogs;

    private List<File> files;


}
