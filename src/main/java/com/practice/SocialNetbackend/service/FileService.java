package com.practice.SocialNetbackend.service;

import com.practice.SocialNetbackend.model.File;
import com.practice.SocialNetbackend.model.Catalog;
import com.practice.SocialNetbackend.model.Person;
import com.practice.SocialNetbackend.repositorie.CatalogRepository;
import com.practice.SocialNetbackend.repositorie.FileRepository;
import com.practice.SocialNetbackend.util.CatalogNotFoundException;
import com.practice.SocialNetbackend.util.FileNotFoundException;
import com.practice.SocialNetbackend.util.NotCreationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
@Transactional(readOnly = true)
public class FileService {

    private final FileRepository fileRepository;
    private final CatalogRepository catalogRepository;

    public FileService(FileRepository fileRepository, CatalogRepository catalogRepository) {
        this.fileRepository = fileRepository;
        this.catalogRepository = catalogRepository;
    }

    @Transactional
    public void save(MultipartFile file, Person person, String pathCatalog) throws CatalogNotFoundException, NotCreationException {
        File saveFile = new File();
        saveFile.setExtension(file.getContentType());
        saveFile.setName(file.getOriginalFilename());
        saveFile.setCatalog(catalogRepository.findByPathAndPerson(pathCatalog, person).
                orElseThrow(() -> new CatalogNotFoundException(String.format("Catalog with path %s not found", pathCatalog))));
        try(InputStream is = file.getInputStream()){
            saveFile.setFile(is.readAllBytes());
        }catch (IOException e){
            throw new NotCreationException("File not readable");
        }
        fileRepository.save(saveFile);
    }

    public File getFile(String name, Catalog catalog) throws FileNotFoundException {
        return fileRepository.findByNameAndCatalog(name, catalog).
                orElseThrow(() -> new FileNotFoundException("File with name " + name + " not found"));
    }

}
