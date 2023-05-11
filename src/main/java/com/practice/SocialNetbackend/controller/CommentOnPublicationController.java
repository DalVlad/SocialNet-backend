package com.practice.SocialNetbackend.controller;

import com.practice.SocialNetbackend.dto.CommentOnPublicationDTO;
import com.practice.SocialNetbackend.model.Client;
import com.practice.SocialNetbackend.model.CommentOnPublication;
import com.practice.SocialNetbackend.model.Publication;
import com.practice.SocialNetbackend.service.CommentOnPublicationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

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

        Client client = new Client();
        client.setId(commentOnPublicationDTO.getClient_id());

        commentOnPublication.setId(commentOnPublicationDTO.getId());
        commentOnPublication.setTextOfComment(commentOnPublicationDTO.getTextOfComment());
        commentOnPublication.setPublication(publication);
        commentOnPublication.setClient(client);

        commentOnPublicationService.createComment(commentOnPublication);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    //@ApiOperation("Удаление комментария")
    @DeleteMapping("/deleteComment/{commentId}")
    public ResponseEntity<?> deleteComment (@PathVariable Long commentId) {
        commentOnPublicationService.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }



    private CommentOnPublicationDTO convertToCommentOnPublicationDTO(CommentOnPublication commentOnPublication){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(commentOnPublication, CommentOnPublicationDTO.class);
    }


//    private CommentOnPublication convertToCommentOnPublication(CommentOnPublicationDTO commentOnPublicationDTO) {                           //Конвертер из DTO
//        CommentOnPublication commentOnPublication = new CommentOnPublication();
//        commentOnPublication.setId(commentOnPublicationDTO.getId());
//        commentOnPublication.setTextOfComment(commentOnPublicationDTO.getTextOfComment());
//        commentOnPublication.
//        commentOnPublication.getClient().setId(commentOnPublicationDTO.getClient_id());
//        return commentOnPublication;
//    }
//    private CommentOnPublication convertToCommentOnPublication(CommentOnPublicationDTO commentOnPublicationDTO) {
//        ModelMapper modelMapper = new ModelMapper();
//        return modelMapper.map(commentOnPublicationDTO, CommentOnPublication.class);
//    }
}
