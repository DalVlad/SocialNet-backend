package com.practice.SocialNetbackend.controller;

import com.practice.SocialNetbackend.security.PersonDetails;
import com.practice.SocialNetbackend.service.CatalogService;
import com.practice.SocialNetbackend.service.FileService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/catalog")
public class CatalogController {


    private final FileService fileService;
    private final CatalogService catalogService;

    public CatalogController(FileService fileService, CatalogService catalogService) {
        this.fileService = fileService;
        this.catalogService = catalogService;
    }


    private PersonDetails getPersonDetails(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (PersonDetails) authentication.getPrincipal();
    }

}
