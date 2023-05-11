package com.practice.SocialNetbackend.service;

import com.practice.SocialNetbackend.dto.PublicationDTO;
import com.practice.SocialNetbackend.model.CommentOnPublication;
import com.practice.SocialNetbackend.model.Community;
import com.practice.SocialNetbackend.model.MemberRole;
import com.practice.SocialNetbackend.model.Publication;
import com.practice.SocialNetbackend.repositorie.CommunityRepository;
import com.practice.SocialNetbackend.repositorie.PublicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static com.practice.SocialNetbackend.model.CommunityRoles.*;


@Service
public class PublicationService {

    @Autowired
    private PublicationRepository publicationRepository;
    @Autowired
    CommunityRepository communityRepository;

    @Transactional
    public void addPublication (PublicationDTO publicationDTO){
        Publication publication = new Publication();
        publication.setId(publicationDTO.getId());
        Community community = new Community();
        community.setId(communityRepository.findIdByName(publicationDTO.getCommunity_name()));
        publication.setOwnCommunity(community);

        publication.setMemberRole(matchRole(publicationDTO));

        List<CommentOnPublication> commentOnPublication = new LinkedList<>();
        publication.setCommentOnPublications(commentOnPublication);
        publication.setMessage(publicationDTO.getMessage());
        publication.setCreatedAt(publicationDTO.getCreatedAt());
        publicationRepository.save(publication);
    }


    @Transactional
    public void updatePost (PublicationDTO publicationDTO){
        Long publicationId = publicationDTO.getId();
        Long roleId = matchRole(publicationDTO).getId();
        String message = publicationDTO.getMessage();
        publicationRepository.updatePublication(roleId, message, publicationId);
    }



    public MemberRole matchRole(PublicationDTO publicationDTO){
        String role = publicationDTO.getMember_role();
        MemberRole memberRole = new MemberRole();
        if (role.equals(ADMIN.toString()))
            memberRole.setId(1L);
        else if (role.equals(MODER.toString()))
            memberRole.setId(2L);
        else if (role.equals(DONATER.toString()))
            memberRole.setId(3L);
        else if (role.equals(SUBSCRIBER.toString())) {
            memberRole.setId(4L);
        } else if (role.equals(NONSUB.toString()))
            memberRole.setId(5L);
        return memberRole;
    }

    @Transactional
    public List<Publication> getAllPublication (String communityName){
            return publicationRepository.findAllByCommunityName(communityName);
    }

    @Transactional
    public Optional<Publication> getPublicationById(Long publicationId){
        return publicationRepository.findById(publicationId);
    }

    @Transactional
    public void deleteById (Long id){
        publicationRepository.deleteById(id);
    }


}
