package com.practice.SocialNetbackend.controller;

import com.practice.SocialNetbackend.dto.StorageDTO;
import com.practice.SocialNetbackend.model.Person;
import com.practice.SocialNetbackend.model.Storage;
import com.practice.SocialNetbackend.security.PersonDetails;
import com.practice.SocialNetbackend.service.StorageService;
import com.practice.SocialNetbackend.service.FileService;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping("/catalog")
public class CatalogController {


    private final FileService fileService;
    private final StorageService storageService;
    private final ModelMapper modelMapper;

    public CatalogController(FileService fileService, StorageService storageService, ModelMapper modelMapper) {
        this.fileService = fileService;
        this.storageService = storageService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @ApiOperation("Get all catalog and file")
    public StorageDTO getAllCatalogAndFile(){
        Person person = getPersonDetails().getPerson();
        return convertToStorageDTO(storageService.getByPerson(person));
    }
    private PersonDetails getPersonDetails(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (PersonDetails) authentication.getPrincipal();
    }

    private StorageDTO convertToStorageDTO(Storage storage){
        return modelMapper.map(storage, StorageDTO.class);
    }

}
