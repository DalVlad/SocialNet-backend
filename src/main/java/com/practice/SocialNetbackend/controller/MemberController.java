package com.practice.SocialNetbackend.controller;


import com.practice.SocialNetbackend.dto.MemberDTO;
import com.practice.SocialNetbackend.dto.MemberRoleDTO;
import com.practice.SocialNetbackend.dto.PersonDTO;
import com.practice.SocialNetbackend.model.CommunityRoles;
import com.practice.SocialNetbackend.model.Member;
import com.practice.SocialNetbackend.model.Person;
import com.practice.SocialNetbackend.service.MemberService;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;
@CrossOrigin
@RestController
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @ApiOperation("Вывод всех подписчиков сообщества")
    @GetMapping("/{communityID}")
    public ResponseEntity<List<MemberDTO>> getAllMembers(@PathVariable("communityID") Long communityID){
        return new ResponseEntity<>(memberService.getAllMembers(communityID)
                .stream()
                .map(this::convertToMemberDTO)
                .collect(Collectors.toList()), HttpStatus.OK);
    }


    @ApiOperation("Подписка")
    @PostMapping("/{communityName}/subscribe")
    public ResponseEntity<MemberDTO> subscribe (@PathVariable("communityName") String communityName,
                                                @RequestBody Person person){
        memberService.addMember(person.getId(), communityName, CommunityRoles.SUBSCRIBER);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation("Отписка")
    @DeleteMapping("/{communityName}/unsubscribe")
    public ResponseEntity<?> unsubscribe (@PathVariable("communityName") String communityName,
                                          @RequestParam Long id) {
        memberService.deleteMember(id, communityName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation("Обновление роли юзера")
    @PatchMapping("/{communityId}/{role}")
    public ResponseEntity<?> setNewRole (@PathVariable("communityId") Long communityId,
                                         @PathVariable("role") String role,
                                         @RequestBody Person person) {
        memberService.setNewRole(person.getId(), communityId, role);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation("Вывод роли юзера")
    @GetMapping("/{communityName}/getRole")
    public ResponseEntity<MemberRoleDTO> getRole (@PathVariable("communityName") String communityName,
                                                  @RequestParam Long id) {
        return new ResponseEntity<>(memberService.getRole(id, communityName), HttpStatus.OK);
    }

    @ApiOperation("Вывод айди юзера")
    @GetMapping("/getUserId/{login}")
    public ResponseEntity<PersonDTO> getUserId (@PathVariable("login") String login) {
        Person person = memberService.getUserId(login);
        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(person.getId());
        personDTO.setLogin(person.getLogin());
        personDTO.setUser_name(person.getUser_name());
        return new ResponseEntity<>(personDTO, HttpStatus.OK);
    }



    private Member convertToMember(MemberDTO memberDTO) {                           //Конвертер из DTO в класс модели
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(memberDTO, Member.class);
    }

    private MemberDTO convertToMemberDTO(Member member) {                           //Конвертер в DTO
        return new MemberDTO(member.getPerson().getId(),
                             member.getPerson().getUser_name(),
                             member.getMemberRole().getRole());                     //TODO добавить картинку юзера
    }
}
