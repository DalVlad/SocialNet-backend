package com.prictice.socialnet.service;

import com.prictice.socialnet.domain.PersonProfile;
import com.prictice.socialnet.utility.exception.PersonNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PersonProfileServiceTest {
    @Autowired
    private PersonProfileService personProfileService;

    public static final String PROFILE_EMAIL = "test@mail.ru";
    public static final String PROFILE_UPDATE_EMAIL = "update@mail.ru";
    public static final String PROFILE_FIRST_NAME = "testFirstName";
    public static final String PROFILE_LAST_NAME = "testLastName";
    public static final LocalDate PROFILE_BIRTHDATE = LocalDate.now();
    public static final Long PROFILE_PERSON_ID = 1L;
    public PersonProfile personProfile = new PersonProfile();
    public PersonProfile personUpdate = new PersonProfile();

    @BeforeEach
    void setUp() {
        personProfile.setEmail(PROFILE_EMAIL);
        personProfile.setFirstName(PROFILE_FIRST_NAME);
        personProfile.setLastName(PROFILE_LAST_NAME);
        personProfile.setBirthdate(PROFILE_BIRTHDATE);

        personUpdate.setEmail(PROFILE_UPDATE_EMAIL);
        personUpdate.setFirstName(PROFILE_FIRST_NAME);
        personUpdate.setLastName(PROFILE_LAST_NAME);
        personUpdate.setBirthdate(PROFILE_BIRTHDATE);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findByIdTest(){
        assertThrows(
                PersonNotFoundException.class,
                () -> personProfileService.findById(2L),
                "Profile was found!"
        );
    }

    @Test
    void createProfileTest(){
        PersonProfile profileTest = personProfileService.createProfile(personProfile, PROFILE_PERSON_ID);
        assertNotNull(profileTest.getId());
    }

    @Test
    void updateProfileTest(){
        PersonProfile profileTest = personProfileService.updateProfile(personProfile, personUpdate);
        assertEquals(profileTest.getEmail(), personUpdate.getEmail());
    }
}