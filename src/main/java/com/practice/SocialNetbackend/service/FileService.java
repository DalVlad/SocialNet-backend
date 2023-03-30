package com.practice.SocialNetbackend.service;

import com.practice.SocialNetbackend.model.File;
import com.practice.SocialNetbackend.model.Storage;
import com.practice.SocialNetbackend.model.Person;
import com.practice.SocialNetbackend.repositorie.StorageRepository;
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
    private final StorageRepository storageRepository;
    private final PathCatalogRepository pathCatalogRepository;

    public FileService(FileRepository fileRepository, StorageRepository storageRepository, PathCatalogRepository pathCatalogRepository) {
        this.fileRepository = fileRepository;
        this.storageRepository = storageRepository;
        this.pathCatalogRepository = pathCatalogRepository;
    }

    @Transactional
    public void save(MultipartFile file, Person person, String pathCatalog) throws CatalogNotFoundException, NotCreationException {
        File saveFile = new File();
        saveFile.setExtension(file.getContentType());
        saveFile.setName(file.getOriginalFilename());
        Storage storage = storageRepository.findByPathAndPerson(pathCatalog, person)
                .orElseThrow(() -> new CatalogNotFoundException(String.format("Catalog with path %s not found", pathCatalog)));
        saveFile.setPathCatalog(pathCatalogRepository.findByPathAndStorage(pathCatalog, storage)
                .orElseThrow(() -> new CatalogNotFoundException(String.format("Catalog with path %s not found", pathCatalog))));
//        saveFile.setStorage(storage);
        try(InputStream is = file.getInputStream()){
            saveFile.setFile(is.readAllBytes());
        }catch (IOException e){
            throw new NotCreationException("File not readable");
        }
        fileRepository.save(saveFile);
    }

    public File getFile(String name, Storage storage, String pathCatalog) throws FileNotFoundException {
        return fileRepository.findByNameAndPathCatalog(name, storage.getPathCatalogs()
                .stream().filter((pathCatalog1 -> pathCatalog.equals(pathCatalog1.getPath()))).findAny()
                .orElseThrow(() -> new CatalogNotFoundException("Catalog with name '" + pathCatalog + "' not found")))
                .orElseThrow(() -> new FileNotFoundException("File with name '" + name + "' in catalog '"+ pathCatalog +"' not found"));
    }

}