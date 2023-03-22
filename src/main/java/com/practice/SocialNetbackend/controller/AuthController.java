package com.practice.SocialNetbackend.controller;


import com.practice.SocialNetbackend.dto.AuthenticationDTO;
import com.practice.SocialNetbackend.dto.PersonDTO;
import com.practice.SocialNetbackend.model.Person;
import com.practice.SocialNetbackend.security.JWTUtil;
import com.practice.SocialNetbackend.service.RegistrationService;
import com.practice.SocialNetbackend.util.PersonNotRegisteringException;
import com.practice.SocialNetbackend.util.PersonValidator;
import com.practice.SocialNetbackend.util.ResponseMessageError;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
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
    public Map<String, String> registration(@RequestBody @Valid PersonDTO personDTO, BindingResult bindingResult){
        Person person = convertToPerson(personDTO);
        personValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors()){
            throw new PersonNotRegisteringException(createErrorMsg(bindingResult.getFieldErrors()));
        }
        registrationService.registration(person);
        String token = jwtUtil.generateToken(person.getLogin());
        return Map.of("jwt", token);
    }

    @PostMapping("/login")
    public Map<String, String> performLogin(@RequestBody AuthenticationDTO authenticationDTO){
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(authenticationDTO.getLogin(), authenticationDTO.getPassword());
        try {
            authenticationManager.authenticate(authenticationToken);
        }catch (BadCredentialsException e){
            return Map.of("message", "Incorrect credentials");
        }
        String token = jwtUtil.generateToken(authenticationDTO.getLogin());
        return Map.of("jwt", token);

    }

    private Person convertToPerson(PersonDTO personDTO){
        return modelMapper.map(personDTO, Person.class);
    }

    private String createErrorMsg(List<FieldError> errors){
        StringBuilder errorMsg = new StringBuilder();
        for(FieldError error: errors){
            errorMsg.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append(";");
        }
        return errorMsg.toString();
    }

    @ExceptionHandler
    private ResponseEntity<ResponseMessageError> personNotRegisteringException(PersonNotRegisteringException e){
        ResponseMessageError response = new ResponseMessageError(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
