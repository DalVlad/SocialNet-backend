package com.practice.SocialNetbackend.controller;

import com.practice.SocialNetbackend.dto.StorageDTO;
import com.practice.SocialNetbackend.model.Person;
import com.practice.SocialNetbackend.model.Storage;
import com.practice.SocialNetbackend.security.PersonDetails;
import com.practice.SocialNetbackend.service.StorageService;
import com.practice.SocialNetbackend.service.FileService;
import com.practice.SocialNetbackend.util.CatalogNotFoundException;
import com.practice.SocialNetbackend.util.NotCreationException;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    @ApiOperation("Create catalog")
    public ResponseEntity<HttpStatus> createCatalog(@RequestParam("pathCatalog") String pathCatalog){
        Storage storage = storageService.getByPerson(getPersonDetails().getPerson());
        storageService.savePathCatalog(pathCatalog, storage);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private PersonDetails getPersonDetails(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (PersonDetails) authentication.getPrincipal();
    }

    private StorageDTO convertToStorageDTO(Storage storage){
        return modelMapper.map(storage, StorageDTO.class);
    }

    @ExceptionHandler
    private ResponseEntity<NotCreationException> notCreationException(NotCreationException e){
        return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<CatalogNotFoundException> catalogNotFound(CatalogNotFoundException e){
        return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
    }

}
