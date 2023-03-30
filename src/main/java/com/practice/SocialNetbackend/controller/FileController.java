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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/catalog")
    @ApiOperation("Save file")
    public ResponseEntity<HttpStatus> saveFile(@RequestParam("file") MultipartFile multipartFile,
                                                   @RequestParam("pathCatalog") String pathCatalog){
        fileService.save(multipartFile, getPersonDetails().getPerson(), pathCatalog);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/catalog")
    @ApiOperation("Get file")
    public ResponseEntity<byte[]> getFile(@RequestParam("pathCatalog") String pathCatalog){
        Person person = getPersonDetails().getPerson();
        int lastSlashIndex = pathCatalog.lastIndexOf("/");
        String catalogName = pathCatalog.substring(0, lastSlashIndex == 0 ? 1 : lastSlashIndex);
        String fileName = pathCatalog.substring(lastSlashIndex + 1);
        Storage storage = storageService.getByPathAndPerson(catalogName, person);
        File file = fileService.getFile(fileName, storage, catalogName);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getExtension()))
                .body(file.getFile());
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
