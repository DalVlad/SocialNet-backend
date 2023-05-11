package com.practice.SocialNetbackend.controller;


import com.practice.SocialNetbackend.dto.MemberDTO;
import com.practice.SocialNetbackend.dto.MemberRoleDTO;
import com.practice.SocialNetbackend.model.Client;
import com.practice.SocialNetbackend.model.CommunityRoles;
import com.practice.SocialNetbackend.model.Member;
import com.practice.SocialNetbackend.service.MemberService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    //@ApiOperation("Вывод всех подписчиков сообщества (id, name, role)")
    @GetMapping("/{communityID}")
    public ResponseEntity<List<MemberDTO>> getAllMembers(@PathVariable("communityID") Long communityID){
        return new ResponseEntity<>(memberService.getAllMembers(communityID)
                .stream()
                .map(this::convertToMemberDTO)
                .collect(Collectors.toList()), HttpStatus.OK);
    }


    //@ApiOperation("Подписка")
    @PostMapping("/{communityName}/subscribe")
    public ResponseEntity<MemberDTO> subscribe (@PathVariable("communityName") String communityName,
                                                @RequestBody Client client){
        memberService.addMember(client.getId(), communityName, CommunityRoles.SUBSCRIBER);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //@ApiOperation("Отписка")
    @DeleteMapping("/{communityName}/unsubscribe")
    public ResponseEntity<?> unsubscribe (@PathVariable("communityName") String communityName,
                                          @RequestParam Long id) {
        memberService.deleteMember(id, communityName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //@ApiOperation("Обновление роли юзера")
    @PatchMapping("/{communityId}/{role}")
    public ResponseEntity<?> setNewRole (@PathVariable("communityId") Long communityId,
                                         @PathVariable("role") String role,
                                         @RequestBody Client client) {
        memberService.setNewRole(client.getId(), communityId, role);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //@ApiOperation("Вывод роли юзера")
    @GetMapping("/{communityName}/getRole")
    public ResponseEntity<MemberRoleDTO> getRole (@PathVariable("communityName") String communityName,
                                                  @RequestParam Long id) {
        return new ResponseEntity<>(memberService.getRole(id, communityName), HttpStatus.OK);
    }



    private Member convertToMember(MemberDTO memberDTO) {                           //Конвертер из DTO в класс модели
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(memberDTO, Member.class);
    }

    private MemberDTO convertToMemberDTO(Member member) {                           //Конвертер в DTO
        return new MemberDTO(member.getClient().getId(),
                             member.getClient().getUsername(),
                             member.getMemberRole().getRole());                     //TODO добавить картинку юзера
    }
}
