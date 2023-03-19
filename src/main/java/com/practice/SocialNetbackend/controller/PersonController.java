package com.practice.SocialNetbackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

    @GetMapping("/hello")
    public String hello(){
        return "Hello";
    }

}
