package com.practice.SocialNetbackend.service;

import com.practice.SocialNetbackend.model.Storage;
import com.practice.SocialNetbackend.model.PathCatalog;
import com.practice.SocialNetbackend.model.Person;
import com.practice.SocialNetbackend.repositorie.StorageRepository;
import com.practice.SocialNetbackend.repositorie.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@Transactional(readOnly = true)
public class RegistrationService {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;
    private final StorageRepository catalogService;

    @Autowired
    public RegistrationService(PersonRepository personRepository, PasswordEncoder passwordEncoder, StorageRepository catalogService) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
        this.catalogService = catalogService;
    }

    @Transactional
    public void registration(Person person){
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        Storage storage = new Storage();
        storage.setPathCatalogRoot(new PathCatalog("/", storage));
        storage.setPerson(person);
        personRepository.save(person);
        catalogService.save(storage);
    }

}
