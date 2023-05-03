package com.practice.SocialNetbackend.controller;


import com.practice.SocialNetbackend.dto.AuthenticationDTO;
import com.practice.SocialNetbackend.mapper.PersonMapper;
import com.practice.SocialNetbackend.model.Person;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin
@Api
public class AuthController {

    private final PersonValidator personValidator;
    private final RegistrationService registrationService;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final PersonMapper personMapper;

    @Autowired
    public AuthController(PersonValidator personValidator, RegistrationService registrationService, JWTUtil jwtUtil, AuthenticationManager authenticationManager, PersonMapper personMapper) {
        this.personValidator = personValidator;
        this.registrationService = registrationService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.personMapper = personMapper;
    }

    @PostMapping("/registration")
    @ApiOperation("Registration person")
    public Map<String, String> registration(@RequestBody @Valid AuthenticationDTO authenticationDTO, BindingResult bindingResult){
        Person person = personMapper.toPerson(authenticationDTO);
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

    @ExceptionHandler
    private ResponseEntity<PersonNotRegisteringException> personNotRegisteringException(PersonNotRegisteringException e){
        return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<AuthenticationException> authenticationException(AuthenticationException e){
        return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
    }

}
