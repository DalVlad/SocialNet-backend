package com.prictice.socialnet.controller;

import com.prictice.socialnet.domain.PersonProfile;
import com.prictice.socialnet.dto.PersonProfileDto;
import com.prictice.socialnet.service.PersonProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("person")
public class PersonProfileController {
    private final PersonProfileService profileService;

    public PersonProfileController(PersonProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("{id}")
    public ResponseEntity<PersonProfile> getProfile(@PathVariable("id") Long id){
        PersonProfile personProfile = profileService.findById(id);
        return ResponseEntity.ok(personProfile);
    }

    @PostMapping ("{id}") /* TODO: Replace Long id -> @AuthenticationPrincipal Person person */
    public ResponseEntity<?> createProfile(@RequestBody PersonProfile personProfile, @PathVariable("id") Long id){
        profileService.createProfile(personProfile, id);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateProfile(@PathVariable("id") PersonProfile fromDb, @RequestBody PersonProfile personProfile){
        profileService.updateProfile(fromDb, personProfile);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
