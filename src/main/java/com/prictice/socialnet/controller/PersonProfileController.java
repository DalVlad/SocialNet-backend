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
    public PersonProfile getProfile(@PathVariable("id")PersonProfile personProfile){
        return personProfile;
    }
}
