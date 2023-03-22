package com.practice.SocialNetbackend.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class PersonDTO {

    @Column(name = "login")
    @Size(min = 3, max = 255, message = "login должен быть от 3 до 255 символов")
    @NotBlank
    @NotNull
    private String login;

    @Column(name = "password")
    @NotBlank
    @NotNull
    private String password;

}
