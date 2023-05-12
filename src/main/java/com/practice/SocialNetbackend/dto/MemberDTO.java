package com.practice.SocialNetbackend.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MemberDTO {
    private Long id;
    private String name;
    private String memberRole;
}
