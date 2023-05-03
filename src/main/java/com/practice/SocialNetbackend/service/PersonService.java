package com.practice.SocialNetbackend.service;

import com.practice.SocialNetbackend.model.Person;
import com.practice.SocialNetbackend.repositorie.PersonRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public boolean isUserExists(String username){
        Optional<Person> person = personRepository.findByLogin(username);
        return person.isPresent();
    }

    public List<Person> getAll(){
        return personRepository.findAll();
    }

    public Person getByLogin(String login){
        return personRepository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
