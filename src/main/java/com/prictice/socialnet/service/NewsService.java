package com.prictice.socialnet.service;

import com.prictice.socialnet.domain.News;
import com.prictice.socialnet.repository.NewsRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {
    private final NewsRepo newsRepo;

    public NewsService(NewsRepo newsRepo) {
        this.newsRepo = newsRepo;
    }

    public List<News> findAllByPersonProfileId(Long id){
        return newsRepo.findAllByPersonProfileId(id);
    }

    public void save(News news){
        newsRepo.save(news);
    }

    public void delete(Long id){
        newsRepo.deleteById(id);
    }
}
