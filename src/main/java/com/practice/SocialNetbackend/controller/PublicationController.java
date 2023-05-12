package com.practice.SocialNetbackend.controller;


import com.practice.SocialNetbackend.dto.CommunityDTO;
import com.practice.SocialNetbackend.dto.PublicationDTO;
import com.practice.SocialNetbackend.model.Publication;
import com.practice.SocialNetbackend.service.PublicationService;
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
@RequestMapping("/community")
public class PublicationController {
    @Autowired
    private PublicationService publicationService;


    @GetMapping("/{nameOfCommunity}/publications")
    public ResponseEntity<List<PublicationDTO>> getAllPublication(@PathVariable("nameOfCommunity") String nameOfCommunity){
        return new ResponseEntity<>(publicationService.getAllPublication(nameOfCommunity)
                .stream()
                .map(this::convertToPublicationDTO)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @ApiOperation("Создание поста")
    @PostMapping("/createPublication")
    public ResponseEntity<CommunityDTO> createCommunity (@RequestBody PublicationDTO publicationDTO){
        publicationService.addPublication(publicationDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation("Возврат данных изменяемого поста")
    @GetMapping("/{nameOfCommunity}/publications/{publicationId}")
    public ResponseEntity<PublicationDTO> getPublicationById(@PathVariable("nameOfCommunity") String nameOfCommunity,
                                                             @PathVariable("publicationId") Long publicationId ){
        if (publicationService.getPublicationById(publicationId).isPresent())
            return new ResponseEntity<>(convertToPublicationDTO(publicationService.getPublicationById(publicationId).get()) , HttpStatus.OK);
        else return null;
    }

    @ApiOperation("Изменение поста")
    @PutMapping("/{nameOfCommunity}/publications/update")
    public ResponseEntity<CommunityDTO> updatePublication (@RequestBody PublicationDTO publicationDTO){
        publicationService.updatePost(publicationDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation("Удаление поста")
    @DeleteMapping("/deletePublication/{publicationId}")
    public ResponseEntity<?> deleteComment (@PathVariable Long publicationId) {
        publicationService.deleteById(publicationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }



    private Publication convertToPublication(PublicationDTO publicationDTO) {                           //Конвертер из DTO в класс модели
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(publicationDTO, Publication.class);
    }

    private PublicationDTO convertToPublicationDTO(Publication publication) {                           //Конвертер в DTO
        return new PublicationDTO(publication.getId(),
                                  publication.getMemberRole().getRole(),
                                  publication.getCreatedAt(),
                                  publication.getMessage(),
                                  null);
    }
}
