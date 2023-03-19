package com.practice.SocialNetbackend.controller;


import com.practice.SocialNetbackend.model.Person;
import com.practice.SocialNetbackend.service.RegistrationService;
import com.practice.SocialNetbackend.util.PersonNotRegisteringException;
import com.practice.SocialNetbackend.util.PersonValidator;
import com.practice.SocialNetbackend.util.ResponseMessageError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final PersonValidator personValidator;
    private final RegistrationService registrationService;

    @Autowired
    public AuthController(PersonValidator personValidator, RegistrationService registrationService) {
        this.personValidator = personValidator;
        this.registrationService = registrationService;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registration(@RequestBody @Valid Person person, BindingResult bindingResult){
        personValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors()){
            throw new PersonNotRegisteringException(createErrorMsg(bindingResult.getFieldErrors()));
        }
        registrationService.registration(person);
        return ResponseEntity.ok(HttpStatus.OK);
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
