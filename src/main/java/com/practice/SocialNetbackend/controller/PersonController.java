package com.practice.SocialNetbackend.controller;

import com.practice.SocialNetbackend.dto.PersonDTO;
import com.practice.SocialNetbackend.mapper.PersonMapper;
import com.practice.SocialNetbackend.service.PersonService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/person")
@CrossOrigin
public class PersonController {

    private final PersonService personService;
    private final PersonMapper personMapper;

    public PersonController(PersonService personService, PersonMapper personMapper) {
        this.personService = personService;
        this.personMapper = personMapper;
    }

    @GetMapping("/hello")
    public String hello(){
        return "Hello";
    }

    @GetMapping
    public List<PersonDTO> getAll(){
        return personMapper.toPersonDTOList(personService.getAll());
    }

}
