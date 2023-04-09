package com.prictice.socialnet.service;

import com.prictice.socialnet.domain.News;
import com.prictice.socialnet.domain.PersonProfile;
import com.prictice.socialnet.repository.NewsRepo;
import com.prictice.socialnet.utility.exception.NewsNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {
    private final NewsRepo newsRepo;
    private final PersonProfileService profileService;

    public NewsService(NewsRepo newsRepo, PersonProfileService profileService) {
        this.newsRepo = newsRepo;
        this.profileService = profileService;
    }

    public News findOne(Long id){
        return newsRepo.findById(id)
                .orElseThrow(() -> new NewsNotFoundException("News with id: " + id + " not found!"));
    }

    public List<News> findAllByPersonProfileId(Long id){
        return newsRepo.findAllByPersonProfileId(id);
    }

    public News createNews(News news, Long id){
        PersonProfile personProfile = profileService.findById(id);
        news.setPersonProfile(personProfile);

        return newsRepo.save(news);
    }

    public News updateNews(News newsFromDb, News news){
        newsFromDb.setText(news.getText());

        return newsRepo.save(newsFromDb);
    }

    public void deleteNews(News news){
        newsRepo.delete(news);
    }
}
