package com.practice.SocialNetbackend.controller;

import com.practice.SocialNetbackend.model.Storage;
import com.practice.SocialNetbackend.model.File;
import com.practice.SocialNetbackend.model.Person;
import com.practice.SocialNetbackend.security.PersonDetails;
import com.practice.SocialNetbackend.service.StorageService;
import com.practice.SocialNetbackend.service.FileService;
import com.practice.SocialNetbackend.util.CatalogNotFoundException;
import com.practice.SocialNetbackend.util.FileNotFoundException;
import com.practice.SocialNetbackend.util.NotCreationException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/file")
@CrossOrigin
@Api
public class FileController {

    private final FileService fileService;
    private final StorageService storageService;

    public FileController(FileService fileService, StorageService storageService) {
        this.fileService = fileService;
        this.storageService = storageService;
    }

    @PostMapping
    @ApiOperation("Save file")
    public ResponseEntity<HttpStatus> saveFile(@RequestParam("file") MultipartFile multipartFile,
                                                   @RequestParam("pathCatalog") String pathCatalog){
        fileService.save(multipartFile, getPersonDetails().getPerson(), pathCatalog);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping
    @ApiOperation("Get file")
    public ResponseEntity<byte[]> getFile(@RequestParam("pathCatalog") String pathCatalogAndFileNameSeparatedSlash){
        Person person = getPersonDetails().getPerson();
        Storage storage = storageService.getByPerson(person);
        File file = fileService.getFile(storage, pathCatalogAndFileNameSeparatedSlash);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(file.getExtension()));
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                .build());
        return new ResponseEntity<>(file.getFile(), headers, HttpStatus.OK);
    }

    @DeleteMapping
    @ApiOperation("Delete file")
    public ResponseEntity<HttpStatus> deleteFile(@RequestParam("pathCatalog") String pathCatalogAndFileNameSeparatedSlash){
        Person person = getPersonDetails().getPerson();
        Storage storage = storageService.getByPerson(person);
        fileService.deleteFile(storage, pathCatalogAndFileNameSeparatedSlash);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private PersonDetails getPersonDetails(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (PersonDetails) authentication.getPrincipal();
    }

    @ExceptionHandler
    private ResponseEntity<NotCreationException> notCreationException(NotCreationException e){
        return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<FileNotFoundException> fileNotFoundException(FileNotFoundException e){
        return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    private ResponseEntity<CatalogNotFoundException> catalogNotFound(CatalogNotFoundException e){
        return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
    }




}
