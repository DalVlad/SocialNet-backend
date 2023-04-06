package com.practice.SocialNetbackend.service;

import com.practice.SocialNetbackend.model.PathCatalog;
import com.practice.SocialNetbackend.model.Storage;
import com.practice.SocialNetbackend.model.Person;
import com.practice.SocialNetbackend.repositorie.PathCatalogRepository;
import com.practice.SocialNetbackend.repositorie.StorageRepository;
import com.practice.SocialNetbackend.util.CatalogNotFoundException;
import com.practice.SocialNetbackend.util.NotCreationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class StorageService {

    private final StorageRepository storageRepository;
    private final PathCatalogRepository pathCatalogRepository;

    public StorageService(StorageRepository storageRepository, PathCatalogRepository pathCatalogRepository) {
        this.storageRepository = storageRepository;
        this.pathCatalogRepository = pathCatalogRepository;
    }

    public Storage getByPerson(Person person) throws CatalogNotFoundException{
        return storageRepository.findByPerson(person)
                        .orElseThrow(() -> new CatalogNotFoundException("Storage not found"));
    }

    public PathCatalog getByPersonAndPath(Person person, String pathCatalog) throws CatalogNotFoundException{
        Storage storage = storageRepository.findByPerson(person)
                .orElseThrow(() -> new CatalogNotFoundException("Storage not found"));
        return storage.getPathCatalogRoot().getPathCatalogs().stream()
                .filter(pathCatalogDTO -> pathCatalogDTO.getPathName().equals(pathCatalog)).findFirst()
                .orElseThrow(() -> new CatalogNotFoundException("Catalog with name '" + pathCatalog + "' not found"));
    }

    @Transactional
    public void savePathCatalog(String path, Storage storage) throws NotCreationException{
        boolean emptyPath = storage.getPathCatalogRoot().getPathCatalogs().stream()
                .noneMatch(pathCatalog -> pathCatalog.getPathName().equals(path));
        if(!emptyPath){
            throw new NotCreationException("Catalog with '" + path + "' is exists");
        }
        PathCatalog pathCatalog = new PathCatalog(path, storage, storage.getPathCatalogRoot());
        storage.getPathCatalogRoot().getPathCatalogs().add(pathCatalog);
        pathCatalogRepository.save(pathCatalog);
    }

}
