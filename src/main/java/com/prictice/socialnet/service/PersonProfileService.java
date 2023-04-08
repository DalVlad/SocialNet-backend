package com.prictice.socialnet.service;

import com.prictice.socialnet.domain.PersonProfile;
import com.prictice.socialnet.repository.PersonProfileRepo;
import com.prictice.socialnet.utility.exception.PersonNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PersonProfileService {
    private final PersonProfileRepo personProfileRepo;

    public PersonProfileService(PersonProfileRepo personProfileRepo) {
        this.personProfileRepo = personProfileRepo;
    }

    public PersonProfile findById(Long id){
        return personProfileRepo.findById(id)
                .orElseThrow(() -> new PersonNotFoundException("Person profile with id: " + id + " not found!"));
    }
}
