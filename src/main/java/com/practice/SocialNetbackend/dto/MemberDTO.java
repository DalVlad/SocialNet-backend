package com.practice.SocialNetbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberDTO {
    private Long id;
    private String name;
    private String memberRole;
}
