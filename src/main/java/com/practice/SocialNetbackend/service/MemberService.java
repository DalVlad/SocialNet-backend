package com.practice.SocialNetbackend.service;

import com.practice.SocialNetbackend.dto.MemberRoleDTO;
import com.practice.SocialNetbackend.model.*;
import com.practice.SocialNetbackend.repositorie.CommunityRepository;
import com.practice.SocialNetbackend.repositorie.MemberRepository;
import com.practice.SocialNetbackend.repositorie.MemberRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

import static com.practice.SocialNetbackend.model.CommunityRoles.*;


@Service
public class MemberService {

    @Autowired
    private MemberRepository repository;
    @Autowired
    private MemberRoleRepository memberRoleRepository;
    @Autowired
    private CommunityRepository communityRepository;

    @Transactional
    public void addMember(Long clientID, String communityName, CommunityRoles role){                                  //ПОДПИСКА
        Client client = new Client();
        client.setId(clientID);

        Community community = new Community();
        community.setId(communityRepository.findIdByName(communityName));

        repository.save(new Member(null, client, community, matchRole(role)));
    }

    @Transactional
    public void setNewRole(Long clientId, Long communityId, String role){
        Long memberRoleID = null;
        if  (role.equals(ADMIN.toString()))
            memberRoleID = 1L;
        else if (role.equals(MODER.toString()))
            memberRoleID = 2L;
        else if (role.equals(DONATER.toString()))
            memberRoleID = 3L;
        else if (role.equals(SUBSCRIBER.toString()))
            memberRoleID = 4L;
        repository.setNewRole(clientId, memberRoleID, communityId);
    }

    public MemberRole matchRole(CommunityRoles role){
        MemberRole memberRole = new MemberRole();
        if  (role.equals(ADMIN))
            memberRole.setId(1L);
        else if (role.equals(MODER))
            memberRole.setId(2L);
        else if (role.equals(DONATER))
            memberRole.setId(3L);
        else if (role.equals(SUBSCRIBER))
            memberRole.setId(4L);
        else memberRole.setId(5L);
        return memberRole;
    }

    @Transactional
    public void deleteMember(Long clientId, String communityName){                                          //ОТПИСКА (Если он не админ)
        if (!getRole(clientId, communityName).getRole().equals(ADMIN.toString())){
            repository.deleteMember(clientId, communityRepository.findIdByName(communityName));
        }
    }


    @Transactional
    public List<Member> getAllMembers(Long communityID){
        return repository.findAllByCommunity_Id(communityID);
    }

    @Transactional
    public MemberRoleDTO getRole(Long clientId, String communityName){
        MemberRole memberRole = memberRoleRepository.getMemberRole(communityName, clientId);
        MemberRoleDTO memberRoleDTO = new MemberRoleDTO();
        if (memberRole!=null){
            memberRoleDTO.setRole(memberRole.getRole());
        } else {
            memberRoleDTO.setRole("NONSUB");
        }
        return memberRoleDTO;
    }


}
