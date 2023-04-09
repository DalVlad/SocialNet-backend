package com.prictice.socialnet.controller;

import com.prictice.socialnet.domain.News;
import com.prictice.socialnet.domain.PersonProfile;
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
    public List<News> findAllById(@PathVariable("id") Long id) {
        return newsService.findAllByPersonProfileId(id);
    }

    @PostMapping
    public News createNews(@RequestBody News news, Long id) {
        return newsService.createNews(news, id);
    }

    @PutMapping("{id}")
    public News updateNews(
            @PathVariable("id") News newsFromDb,
            @RequestBody News news
    ) {
        return newsService.updateNews(newsFromDb, news);
    }

    @DeleteMapping("{id}")
    public void deleteNews(@PathVariable("id") News news){
        newsService.deleteNews(news);
    }
}
