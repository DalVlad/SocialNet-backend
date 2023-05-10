package com.prictice.socialnet.dto;

import com.prictice.socialnet.domain.PersonProfile;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PersonProfileDto {
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate birthdate;

    public PersonProfileDto(PersonProfile personProfile) {
        this.email = personProfile.getEmail();
        this.firstName = personProfile.getFirstName();
        this.lastName = personProfile.getLastName();
        this.birthdate = personProfile.getBirthdate();
    }
}
