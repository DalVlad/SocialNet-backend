package com.practice.SocialNetbackend.controller;

import com.practice.SocialNetbackend.security.PersonDetails;
import com.practice.SocialNetbackend.service.StorageService;
import com.practice.SocialNetbackend.service.FileService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/catalog")
public class CatalogController {


    private final FileService fileService;
    private final StorageService storageService;

    public CatalogController(FileService fileService, StorageService storageService) {
        this.fileService = fileService;
        this.storageService = storageService;
    }


    private PersonDetails getPersonDetails(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (PersonDetails) authentication.getPrincipal();
    }

}
