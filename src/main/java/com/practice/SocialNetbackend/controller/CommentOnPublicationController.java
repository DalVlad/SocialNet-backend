package com.practice.SocialNetbackend.controller;

import com.practice.SocialNetbackend.dto.CommentOnPublicationDTO;

import com.practice.SocialNetbackend.model.CommentOnPublication;
import com.practice.SocialNetbackend.model.Person;
import com.practice.SocialNetbackend.model.Publication;
import com.practice.SocialNetbackend.service.CommentOnPublicationService;
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
@Api("Контроллер комментариев у постов")
@RestController
@RequestMapping("/publications")
public class CommentOnPublicationController {

    @Autowired
    private CommentOnPublicationService commentOnPublicationService;

    @GetMapping("/{id}/comment")
    public ResponseEntity<List<CommentOnPublicationDTO>> getCommentsOnPublication(@PathVariable Long id){
        return new ResponseEntity<>(commentOnPublicationService.getCommentsOnPublication(id)
                .stream()
                .map(this::convertToCommentOnPublicationDTO)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @PostMapping("/{publicationID}/createComment")
    public ResponseEntity<CommentOnPublicationDTO> createCommentsOnPublication(@PathVariable Long publicationID,
                                                                               @RequestBody CommentOnPublicationDTO commentOnPublicationDTO){

        CommentOnPublication commentOnPublication = new CommentOnPublication();

        Publication publication = new Publication();
        publication.setId(publicationID);
        Person person = new Person();
        person.setId(commentOnPublicationDTO.getPerson_id());
        commentOnPublication.setId(commentOnPublicationDTO.getId());
        commentOnPublication.setTextOfComment(commentOnPublicationDTO.getTextOfComment());
        commentOnPublication.setPublication(publication);
        commentOnPublication.setPerson(person);

        commentOnPublicationService.createComment(commentOnPublication);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @ApiOperation("Удаление комментария")
    @DeleteMapping("/deleteComment/{commentId}")
    public ResponseEntity<?> deleteComment (@PathVariable Long commentId) {
        commentOnPublicationService.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    private CommentOnPublicationDTO convertToCommentOnPublicationDTO(CommentOnPublication commentOnPublication){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(commentOnPublication, CommentOnPublicationDTO.class);
    }


}
