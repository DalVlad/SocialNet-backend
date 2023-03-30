package com.practice.SocialNetbackend.service;

import com.practice.SocialNetbackend.model.Storage;
import com.practice.SocialNetbackend.model.PathCatalog;
import com.practice.SocialNetbackend.model.Person;
import com.practice.SocialNetbackend.repositorie.StorageRepository;
import com.practice.SocialNetbackend.util.CatalogNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
public class StorageService {

    private final StorageRepository storageRepository;

    public StorageService(StorageRepository storageRepository) {
        this.storageRepository = storageRepository;
    }

    public Storage getById(long id){
        return storageRepository.findById(id).orElseThrow(() -> new CatalogNotFoundException("Catalog not found"));
    }

    public Storage getByPathAndPerson(String path, Person person){
        PathCatalog pathCatalog = new PathCatalog();
        pathCatalog.setPath(path);
        return storageRepository.findByPathAndPerson(path, person)
                        .orElseThrow(() -> new CatalogNotFoundException("Catalog with path '" + path + "' not found"));
    }


}
