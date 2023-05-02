package com.prictice.socialnet.controller;

import com.prictice.socialnet.domain.Comment;
import com.prictice.socialnet.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("comment")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /* TODO исправить запрос
        (возвращает {
            commment {
                text,
                news {
                    ... ,
                    personProfile{...}
                },
                personProfile{...}
            }
        })*/
    @GetMapping("{id}")
    public ResponseEntity<List<Comment>> findByNewsId(@PathVariable("id") Long id){
        List<Comment> comments = commentService.findAllByNewsId(id);
        return ResponseEntity.ok(comments);
    }

    @PostMapping("{id}")
    public ResponseEntity<?> createComment(@RequestBody Comment comment,
                                           @PathVariable("id") Long newsId,
                                           @RequestParam("newsId") Long personId){
        commentService.createComment(comment, personId, newsId);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateComment(@PathVariable("id") Comment fromDb, @RequestBody Comment comment){
        commentService.updateComment(fromDb, comment);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteComment(@PathVariable("id") Comment comment){
        commentService.delete(comment);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
