package com.practice.SocialNetbackend.service;

import com.practice.SocialNetbackend.model.File;
import com.practice.SocialNetbackend.model.Catalog;
import com.practice.SocialNetbackend.model.Person;
import com.practice.SocialNetbackend.repositorie.CatalogRepository;
import com.practice.SocialNetbackend.repositorie.FileRepository;
import com.practice.SocialNetbackend.repositorie.PathCatalogRepository;
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
    private final PathCatalogRepository pathCatalogRepository;

    public FileService(FileRepository fileRepository, CatalogRepository catalogRepository, PathCatalogRepository pathCatalogRepository) {
        this.fileRepository = fileRepository;
        this.catalogRepository = catalogRepository;
        this.pathCatalogRepository = pathCatalogRepository;
    }

    @Transactional
    public void save(MultipartFile file, Person person, String pathCatalog) throws CatalogNotFoundException, NotCreationException {
        File saveFile = new File();
        saveFile.setExtension(file.getContentType());
        saveFile.setName(file.getOriginalFilename());
        Catalog catalog = catalogRepository.findByPathAndPerson(pathCatalog, person)
                .orElseThrow(() -> new CatalogNotFoundException(String.format("Catalog with path %s not found", pathCatalog)));
        saveFile.setPathCatalog(pathCatalogRepository.findByPathAndCatalog(pathCatalog, catalog)
                .orElseThrow(() -> new CatalogNotFoundException(String.format("Catalog with path %s not found", pathCatalog))));
        saveFile.setCatalog(catalog);
        try(InputStream is = file.getInputStream()){
            saveFile.setFile(is.readAllBytes());
        }catch (IOException e){
            throw new NotCreationException("File not readable");
        }
        fileRepository.save(saveFile);
    }

    public File getFile(String name, Catalog catalog, String pathCatalog) throws FileNotFoundException {
        return fileRepository.findByNameAndPathCatalog(name, catalog.getPathCatalogs()
                .stream().filter((pathCatalog1 -> pathCatalog.equals(pathCatalog1.getPath()))).findAny()
                .orElseThrow(() -> new CatalogNotFoundException("Catalog with name '" + pathCatalog + "' not found")))
                .orElseThrow(() -> new FileNotFoundException("File with name '" + name + "' in catalog '"+ pathCatalog +"' not found"));
    }

}
