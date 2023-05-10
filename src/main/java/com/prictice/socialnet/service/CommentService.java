package com.prictice.socialnet.service;

import com.prictice.socialnet.domain.Comment;
import com.prictice.socialnet.dto.CommentDto;
import com.prictice.socialnet.repository.CommentRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepo commentRepo;
    private final PersonProfileService profileService;
    private final NewsService newsService;

    public CommentService(CommentRepo commentRepo, PersonProfileService profileService, NewsService newsService) {
        this.commentRepo = commentRepo;
        this.profileService = profileService;
        this.newsService = newsService;
    }

    public List<CommentDto> findAllByNewsId(Long id){
        List<Comment> commentList = commentRepo.findAllByNewsId(id);
        return commentList.stream().map(CommentDto::new).collect(Collectors.toList());
    }

    public Comment createComment(Comment comment, Long personId, Long newsId){
        comment.setPersonProfile(profileService.findById(personId));
        comment.setNews(newsService.findOne(newsId));
        return commentRepo.save(comment);
    }

    public void delete(Comment comment){
        commentRepo.delete(comment);
    }

    public Comment updateComment(Comment fromDb, Comment comment) {
        fromDb.setMessage(comment.getMessage());
        return commentRepo.save(fromDb);
    }
}
