package com.prictice.socialnet.controller;

import com.prictice.socialnet.domain.News;
import com.prictice.socialnet.dto.NewsDto;
import com.prictice.socialnet.service.NewsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("news")
@CrossOrigin
@Api
public class NewsController {
    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @ApiOperation("Get all person news")
    @GetMapping("{id}")
    public ResponseEntity<List<NewsDto>> findAllById(@PathVariable("id") Long id) {
        List<NewsDto> personNews = newsService.findAllByPersonProfileId(id);
        return ResponseEntity.ok(personNews);
    }

    @ApiOperation("Set like on news")
    @PostMapping("like")
    public void likeNews(@RequestBody News news, @RequestParam("personId") Long id){
        newsService.setLike(news, id);
    }

    @ApiOperation("Create news")
    @PostMapping
    public ResponseEntity<?> createNews(@RequestBody News news, @RequestParam("personId") Long id) {
        newsService.createNews(news, id);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @ApiOperation("Update news")
    @PutMapping("{id}")
    public ResponseEntity<?> updateNews(@PathVariable("id") News fromDb, @RequestBody News news) {
        newsService.updateNews(fromDb, news);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ApiOperation("Delete news")
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteNews(@PathVariable("id") News news){
        newsService.deleteNews(news);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
