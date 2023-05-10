package com.prictice.socialnet.controller;

import com.prictice.socialnet.domain.News;
import com.prictice.socialnet.dto.NewsDto;
import com.prictice.socialnet.service.NewsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("news")
public class NewsController {
    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("{id}")
    public ResponseEntity<List<NewsDto>> findAllById(@PathVariable("id") Long id) {
        List<NewsDto> personNews = newsService.findAllByPersonProfileId(id);
        return ResponseEntity.ok(personNews);
    }

    @PostMapping
    public ResponseEntity<?> createNews(@RequestBody News news, @RequestParam("personId") Long id) {
        newsService.createNews(news, id);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateNews(@PathVariable("id") News fromDb, @RequestBody News news) {
        newsService.updateNews(fromDb, news);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteNews(@PathVariable("id") News news){
        newsService.deleteNews(news);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
