package com.practice.SocialNetbackend.mapper;

import com.practice.SocialNetbackend.dto.AuthenticationDTO;
import com.practice.SocialNetbackend.dto.PersonDTO;
import com.practice.SocialNetbackend.model.Person;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PersonMapper {


    private ModelMapper modelMapper;

    public PersonMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Person toPerson(AuthenticationDTO authenticationDTO){
        return modelMapper.map(authenticationDTO, Person.class);
    }

    public List<PersonDTO> toPersonDTOList(List<Person> personList){
        return personList.stream().map(person -> modelMapper.map(person, PersonDTO.class)).collect(Collectors.toList());
    }

}
