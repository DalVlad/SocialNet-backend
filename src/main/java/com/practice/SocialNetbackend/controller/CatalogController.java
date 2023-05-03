package com.practice.SocialNetbackend.controller;

import com.practice.SocialNetbackend.dto.PathCatalogDTO;
import com.practice.SocialNetbackend.dto.StorageDTO;
import com.practice.SocialNetbackend.model.PathCatalog;
import com.practice.SocialNetbackend.model.Person;
import com.practice.SocialNetbackend.model.Storage;
import com.practice.SocialNetbackend.security.PersonDetails;
import com.practice.SocialNetbackend.service.PersonService;
import com.practice.SocialNetbackend.service.StorageService;
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
    private final StorageService storageService;
    private final ModelMapper modelMapper;
    private final PersonService personService;

    public CatalogController(StorageService storageService, ModelMapper modelMapper, PersonService personService) {
        this.storageService = storageService;
        this.modelMapper = modelMapper;
        this.personService = personService;
    }

    @GetMapping
    @ApiOperation("Get all catalog and file")
    public PathCatalogDTO getCatalogAndFile(@RequestParam("pathCatalog") String pathCatalog,
                                            @RequestParam(value = "l", required = false, defaultValue = "") String login){
        Person person;
        if(login.isEmpty() && login.isBlank()){
            person = getPersonDetails().getPerson();
        }else {
            person = personService.getByLogin(login);
        }
        return convertToPathCatalogDTO(storageService.getByPersonAndPath(person, pathCatalog));
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
    private PathCatalogDTO convertToPathCatalogDTO(PathCatalog pathCatalog){
        return modelMapper.map(pathCatalog, PathCatalogDTO.class);
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
