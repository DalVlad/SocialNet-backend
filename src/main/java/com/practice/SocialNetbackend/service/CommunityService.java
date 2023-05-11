package com.practice.SocialNetbackend.service;


import com.practice.SocialNetbackend.dto.MemberDTO;
import com.practice.SocialNetbackend.model.Community;
import com.practice.SocialNetbackend.model.CommunityRoles;
import com.practice.SocialNetbackend.repositorie.CommunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class CommunityService {
    @Autowired
    private CommunityRepository repository;
    @Autowired
    private MemberService memberService;

    @Transactional
    public Community addCommunity (Community community, MemberDTO memberDTO){
        repository.save(community);
        memberService.addMember(memberDTO.getId(), community.getName(), CommunityRoles.ADMIN);
        return community;
    }

    @Transactional
    public List<Community> getAllCommunity (){
        return repository.findAll();
    }

    @Transactional
    public void updateCommunity (Community newCommunity){
        //repository.update(community);                           // TODO
    }

    @Transactional
    public void deleteById (Long id){
        repository.deleteById(id);
    }

    @Transactional
    public Optional<Community> getByName(String name){
        return repository.findByName(name);
    }

    @Transactional
    public Optional<Community> getById(Long id){
        return repository.findById(id);
    }

    public Long getIdByName(String name){
        return repository.findIdByName(name);
    }
}
