package com.practice.SocialNetbackend.service;

import com.practice.SocialNetbackend.model.Storage;
import com.practice.SocialNetbackend.model.Person;
import com.practice.SocialNetbackend.repositorie.StorageRepository;
import com.practice.SocialNetbackend.util.CatalogNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
public class StorageService {

    private final StorageRepository storageRepository;

    public StorageService(StorageRepository storageRepository) {
        this.storageRepository = storageRepository;
    }

    public Storage getByPerson(Person person){
        return storageRepository.findByPerson(person)
                        .orElseThrow(() -> new CatalogNotFoundException("Storage not found"));
    }

    public Storage getByPersonOnlyWithRootCatalog(Person person){
        Storage storage = storageRepository.findByPerson(person).orElseThrow(() -> new CatalogNotFoundException("Storage not found"));
        storage.setPathCatalogs(storage.getPathCatalogs().stream().filter((pathCatalog) -> pathCatalog.getPathName().equals("/")).collect(Collectors.toList()));
        return storage;
    }


}
