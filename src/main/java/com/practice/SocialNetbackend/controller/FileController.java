package com.practice.SocialNetbackend.controller;

import com.practice.SocialNetbackend.dto.FileLikeDTO;
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

import java.util.concurrent.TimeUnit;


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

    @PostMapping("/img")
    @ApiOperation("Save img")
    public ResponseEntity<HttpStatus> saveImg(@RequestParam("file") MultipartFile multipartFile,
                                                   @RequestParam("pathCatalog") String pathCatalog){
        fileService.save(multipartFile, getPersonDetails().getPerson(), pathCatalog, multipartFile);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/video")
    @ApiOperation("Save video")
    public ResponseEntity<HttpStatus> saveVideo(@RequestParam("file") MultipartFile multipartFile,
                                                  @RequestParam("preview") MultipartFile multipartFilePreview,
                                               @RequestParam("pathCatalog") String pathCatalog){
        fileService.save(multipartFile, getPersonDetails().getPerson(), pathCatalog, multipartFilePreview);
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
        headers.setCacheControl(CacheControl.maxAge(30, TimeUnit.MINUTES));
        return new ResponseEntity<>(file.getFile(), headers, HttpStatus.OK);
    }

    @GetMapping("/like")
    @ApiOperation("Get file-like")
    public FileLikeDTO getFileLike(@RequestParam("pathCatalog") String pathCatalogAndFileNameSeparatedSlash){
        Person person = getPersonDetails().getPerson();
        Storage storage = storageService.getByPerson(person);
        return fileService.getFileLike(person, fileService.getFile(storage, pathCatalogAndFileNameSeparatedSlash));
    }

    @PostMapping("/like")
    @ApiOperation("Set file-like")
    public void setFileLike(@RequestParam("pathCatalog") String pathCatalogAndFileNameSeparatedSlash){
        Person person = getPersonDetails().getPerson();
        Storage storage = storageService.getByPerson(person);
        File file = fileService.getFile(storage, pathCatalogAndFileNameSeparatedSlash);

        fileService.setFileLike(person, fileService.getFile(storage, pathCatalogAndFileNameSeparatedSlash));
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
