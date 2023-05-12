package com.practice.SocialNetbackend.controller;


import com.practice.SocialNetbackend.dto.AuthenticationDTO;
import com.practice.SocialNetbackend.dto.PersonDTO;
import com.practice.SocialNetbackend.model.CommentOnPublication;
import com.practice.SocialNetbackend.model.Member;
import com.practice.SocialNetbackend.model.Person;
import com.practice.SocialNetbackend.model.Storage;
import com.practice.SocialNetbackend.security.JWTUtil;
import com.practice.SocialNetbackend.service.RegistrationService;
import com.practice.SocialNetbackend.util.PersonNotRegisteringException;
import com.practice.SocialNetbackend.util.PersonValidator;
import com.practice.SocialNetbackend.util.ResponseMessageError;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin
@Api
public class AuthController {

    private final PersonValidator personValidator;
    private final RegistrationService registrationService;
    private final JWTUtil jwtUtil;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(PersonValidator personValidator, RegistrationService registrationService, JWTUtil jwtUtil, ModelMapper modelMapper, AuthenticationManager authenticationManager) {
        this.personValidator = personValidator;
        this.registrationService = registrationService;
        this.jwtUtil = jwtUtil;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/registration")
    @ApiOperation("Registration person")
    public Map<String, String> registration(@RequestBody @Valid PersonDTO personDTO, BindingResult bindingResult){
        Person person = convertToPerson(personDTO);
        personValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors()){
            throw new PersonNotRegisteringException(ResponseMessageError.createErrorMsg(bindingResult.getFieldErrors()));
        }
        registrationService.registration(person);
        String token = jwtUtil.generateToken(person.getLogin());
        return Map.of("jwt", token);
    }

    @PostMapping("/login")
    @ApiOperation("Login person")
    public Map<String, String> performLogin(@RequestBody AuthenticationDTO authenticationDTO){
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(authenticationDTO.getLogin(), authenticationDTO.getPassword());
        authenticationManager.authenticate(authenticationToken);
        String token = jwtUtil.generateToken(authenticationDTO.getLogin());
        return Map.of("jwt", token);
    }

    private Person convertToPerson(PersonDTO personDTO){
        Person person = new Person();
        person.setUser_name(personDTO.getUser_name());
        person.setLogin(personDTO.getLogin());
        person.setPassword(personDTO.getPassword());
        List<Member> members = new LinkedList<>();
        person.setMembers(members);
        List<CommentOnPublication> commentOnPublication = new LinkedList<>();
        person.setCommentOnPublications(commentOnPublication);
        List<Storage> storages = new LinkedList<>();
        person.setStorages(storages);
        return person;
    }

    @ExceptionHandler
    private ResponseEntity<PersonNotRegisteringException> personNotRegisteringException(PersonNotRegisteringException e){
        return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<AuthenticationException> authenticationException(AuthenticationException e){
        return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
    }

}
