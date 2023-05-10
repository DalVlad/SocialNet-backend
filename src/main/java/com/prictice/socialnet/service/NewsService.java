package com.prictice.socialnet.service;

import com.prictice.socialnet.domain.News;
import com.prictice.socialnet.dto.NewsDto;
import com.prictice.socialnet.repository.NewsRepo;
import com.prictice.socialnet.utility.exception.NewsNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsService {
    private final NewsRepo newsRepo;
    private final PersonProfileService profileService;

    public NewsService(NewsRepo newsRepo, PersonProfileService profileService) {
        this.newsRepo = newsRepo;
        this.profileService = profileService;
    }

    // maybe make void?
    public News findOne(Long id){
        return newsRepo.findById(id)
                .orElseThrow(() -> new NewsNotFoundException("News with id: " + id + " not found!"));
    }

    public List<NewsDto> findAllByPersonProfileId(Long id){
        List<News> newsList = newsRepo.findAllByPersonProfileId(id);
        return newsList.stream().map(NewsDto::new).collect(Collectors.toList());
    }

    public News createNews(News news, Long id){
        news.setPersonProfile(profileService.findById(id));
        return newsRepo.save(news);
    }

    public News updateNews(News newsFromDb, News news){
        newsFromDb.setText(news.getText());
        newsFromDb.setPicture(news.getPicture());
        return newsRepo.save(newsFromDb);
    }

    public void deleteNews(News news){
        newsRepo.delete(news);
    }
}
