package com.prictice.socialnet.service;

import com.prictice.socialnet.domain.Comment;
import com.prictice.socialnet.repository.CommentRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepo commentRepo;

    public CommentService(CommentRepo commentRepo) {
        this.commentRepo = commentRepo;
    }

    public List<Comment> findAllByNewsId(Long id){
        return commentRepo.findAllByNewsId(id);
    }

    public void save(Comment comment){
        commentRepo.save(comment);
    }

    public void delete(Long id){
        commentRepo.deleteById(id);
    }
}
