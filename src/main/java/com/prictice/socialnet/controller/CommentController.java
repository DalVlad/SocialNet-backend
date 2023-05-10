package com.prictice.socialnet.controller;

import com.prictice.socialnet.domain.Comment;
import com.prictice.socialnet.dto.CommentDto;
import com.prictice.socialnet.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("comment")
@CrossOrigin
@Api
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @ApiOperation("Get all comments by news")
    @GetMapping("{id}")
    public ResponseEntity<List<CommentDto>> findByNewsId(@PathVariable("id") Long id){
        List<CommentDto> comments = commentService.findAllByNewsId(id);
        return ResponseEntity.ok(comments);
    }

    @ApiOperation("Create comment")
    @PostMapping("{id}")
    public ResponseEntity<?> createComment(@RequestBody Comment comment,
                                           @PathVariable("id") Long newsId,
                                           @RequestParam("newsId") Long personId){
        commentService.createComment(comment, personId, newsId);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @ApiOperation("Update comment")
    @PutMapping("{id}")
    public ResponseEntity<?> updateComment(@PathVariable("id") Comment fromDb, @RequestBody Comment comment){
        commentService.updateComment(fromDb, comment);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ApiOperation("Delete comment")
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteComment(@PathVariable("id") Comment comment){
        commentService.delete(comment);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
