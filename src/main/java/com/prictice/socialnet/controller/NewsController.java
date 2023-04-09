package com.prictice.socialnet.controller;

import com.prictice.socialnet.domain.News;
import com.prictice.socialnet.service.NewsService;
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
    public List<News> findAllById(@PathVariable("id") Long id){
        return newsService.findAllByPersonProfileId(id);
    }
}
