package com.prictice.socialnet.controller;

import com.prictice.socialnet.domain.PersonProfile;
import com.prictice.socialnet.service.PersonProfileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("person")
@CrossOrigin
@Api
public class PersonProfileController {
    private final PersonProfileService profileService;

    public PersonProfileController(PersonProfileService profileService) {
        this.profileService = profileService;
    }

    @ApiOperation("Get person profile")
    @GetMapping("{id}")
    public ResponseEntity<PersonProfile> getProfile(@PathVariable("id") Long id){
        PersonProfile personProfile = profileService.findById(id);
        return ResponseEntity.ok(personProfile);
    }

    @ApiOperation("Create person profile")
    @PostMapping ("{id}") /* TODO: Replace Long id -> @AuthenticationPrincipal Person person */
    public ResponseEntity<?> createProfile(@RequestBody PersonProfile personProfile, @PathVariable("id") Long id){
        profileService.createProfile(personProfile, id);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @ApiOperation("Update profile")
    @PutMapping("{id}")
    public ResponseEntity<?> updateProfile(@PathVariable("id") PersonProfile fromDb, @RequestBody PersonProfile personProfile){
        profileService.updateProfile(fromDb, personProfile);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
