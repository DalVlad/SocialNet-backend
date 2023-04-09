package com.prictice.socialnet.controller;

import com.prictice.socialnet.domain.PersonProfile;
import com.prictice.socialnet.service.PersonProfileService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("person")
public class PersonProfileController {
    private final PersonProfileService profileService;

    public PersonProfileController(PersonProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("{id}")
    public PersonProfile getProfile(@PathVariable("id") Long id){
        return profileService.findById(id);
    }

    @PostMapping  /* Replace Long id -> @AuthenticationPrincipal Person person */
    public PersonProfile createProfile(@RequestBody PersonProfile personProfile, Long id){
        return profileService.createProfile(personProfile, id);
    }

    @PostMapping("{id}")
    public PersonProfile updateProfile(
            @PathVariable("id") PersonProfile personProfileFromDb,
            @RequestBody PersonProfile personProfile
    ){
        return profileService.updateProfile(personProfileFromDb, personProfile);
    }
}
