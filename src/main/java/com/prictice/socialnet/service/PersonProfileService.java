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

    public PersonProfile createProfile(PersonProfile personProfile, Long id){
        personProfile.setPerson(id);

        return personProfileRepo.save(personProfile);
    }

    public PersonProfile updateProfile(
            PersonProfile personProfileFromDb,
            PersonProfile personProfile
    ){
        personProfileFromDb.setFirstName(personProfile.getFirstName());
        personProfileFromDb.setLastName(personProfile.getLastName());
        personProfileFromDb.setEmail(personProfile.getEmail());
        personProfileFromDb.setBirthdate(personProfile.getBirthdate());

        return personProfileRepo.save(personProfileFromDb);
    }
}
