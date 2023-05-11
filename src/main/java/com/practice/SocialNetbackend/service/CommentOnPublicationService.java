package com.practice.SocialNetbackend.service;

import com.practice.SocialNetbackend.model.CommentOnPublication;
import com.practice.SocialNetbackend.repositorie.CommentOnPublicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class CommentOnPublicationService {
    @Autowired
    private CommentOnPublicationRepository repository;

    public List<CommentOnPublication> getCommentsOnPublication (Long publicationID){
        return repository.getComments(publicationID);

    }
    public void deleteComment (Long commentID){
        repository.deleteById(commentID);
    };
    public void createComment (CommentOnPublication commentOnPublication){
        repository.save(commentOnPublication);
    }
}
