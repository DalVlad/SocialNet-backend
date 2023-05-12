package com.practice.SocialNetbackend.controller;

import com.practice.SocialNetbackend.dto.CommunityCreationDTO;
import com.practice.SocialNetbackend.dto.CommunityDTO;
import com.practice.SocialNetbackend.dto.MemberDTO;
import com.practice.SocialNetbackend.model.Community;
import com.practice.SocialNetbackend.service.CommunityService;
import com.practice.SocialNetbackend.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;
@CrossOrigin
@Api("Контроллер сообществ")
@RestController
@RequestMapping("/community")
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    @Autowired
    private MemberService memberService;

    @ApiOperation("Вывод всех сообществ")
    @GetMapping("/all")
    public ResponseEntity<List<CommunityDTO>> getAllCommunity(){
        return new ResponseEntity<>(communityService.getAllCommunity()
                .stream()
                .map(this::convertToCommunityDTO)
                .collect(Collectors.toList()), HttpStatus.OK);
    }
    @ApiOperation("Вывод страницы сообщества")
    @GetMapping("/{nameOfCommunity}")
    public ResponseEntity<CommunityDTO> getCommunityPage(@PathVariable("nameOfCommunity") String name){
        if (communityService.getByName(name).isPresent())
            return new ResponseEntity<>((convertToCommunityDTO(communityService.getByName(name).get())), HttpStatus.OK);
        else return null;
    }

    @ApiOperation("Создание сообщества")
    @PostMapping("/create")
    public ResponseEntity<CommunityDTO> createCommunity (@RequestBody CommunityCreationDTO communityCreationDTO){
        CommunityDTO communityDTO = communityCreationDTO.getCommunityDTO();
        MemberDTO memberDTO = communityCreationDTO.getMemberDTO();

        communityService.addCommunity(convertToCommunity(communityDTO), memberDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @ApiOperation("Удаление сообщества")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCommunity (@PathVariable("id") Long id){
        communityService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }




    private Community convertToCommunity(CommunityDTO communityDTO) {                           //Конвертер из DTO в класс модели
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(communityDTO, Community.class);
    }

    private CommunityDTO convertToCommunityDTO(Community community) {                           //Конвертер в DTO
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(community, CommunityDTO.class);
    }
}
