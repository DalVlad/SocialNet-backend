package com.practice.SocialNetbackend.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class PersonDTO {

    @Size(min = 3, max = 255, message = "login должен быть от 3 до 255 символов")
    @NotBlank
    @NotNull
    private String login;

    @NotBlank
    @NotNull
    private String password;

}
