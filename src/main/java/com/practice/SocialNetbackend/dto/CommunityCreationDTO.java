package com.practice.SocialNetbackend.dto;

public class CommunityCreationDTO {
    private MemberDTO memberDTO;
    private CommunityDTO communityDTO;

    public CommunityCreationDTO(MemberDTO memberDTO, CommunityDTO communityDTO) {
        this.memberDTO = memberDTO;
        this.communityDTO = communityDTO;
    }

    public CommunityCreationDTO() {
    }

    public MemberDTO getMemberDTO() {
        return memberDTO;
    }

    public void setMemberDTO(MemberDTO memberDTO) {
        this.memberDTO = memberDTO;
    }

    public CommunityDTO getCommunityDTO() {
        return communityDTO;
    }

    public void setCommunityDTO(CommunityDTO communityDTO) {
        this.communityDTO = communityDTO;
    }
}
