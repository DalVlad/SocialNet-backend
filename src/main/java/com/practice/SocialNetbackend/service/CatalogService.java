package com.practice.SocialNetbackend.service;

import com.practice.SocialNetbackend.model.Catalog;
import com.practice.SocialNetbackend.model.PathCatalog;
import com.practice.SocialNetbackend.model.Person;
import com.practice.SocialNetbackend.repositorie.CatalogRepository;
import com.practice.SocialNetbackend.util.CatalogNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
public class CatalogService {

    private final CatalogRepository catalogRepository;

    public CatalogService(CatalogRepository catalogRepository) {
        this.catalogRepository = catalogRepository;
    }

    public Catalog getById(long id){
        return catalogRepository.findById(id).orElseThrow(() -> new CatalogNotFoundException("Catalog not found"));
    }

    public Catalog getByPathAndPerson(String path, Person person){
        PathCatalog pathCatalog = new PathCatalog();
        pathCatalog.setPath(path);
        return catalogRepository.findByPathAndPerson(path, person)
                        .orElseThrow(() -> new CatalogNotFoundException("Catalog with path '" + path + "' not found"));
    }


}
