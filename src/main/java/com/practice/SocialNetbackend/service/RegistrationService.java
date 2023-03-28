package com.practice.SocialNetbackend.service;

import com.practice.SocialNetbackend.model.Catalog;
import com.practice.SocialNetbackend.model.Person;
import com.practice.SocialNetbackend.repositorie.CatalogRepository;
import com.practice.SocialNetbackend.repositorie.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class RegistrationService {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;
    private final CatalogRepository catalogService;

    @Autowired
    public RegistrationService(PersonRepository personRepository, PasswordEncoder passwordEncoder, CatalogRepository catalogService) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
        this.catalogService = catalogService;
    }

    @Transactional
    public void registration(Person person){
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        Catalog catalog = new Catalog();
        catalog.setPath("/");
        catalog.setPerson(person);
        personRepository.save(person);
        catalogService.save(catalog);
    }

}
