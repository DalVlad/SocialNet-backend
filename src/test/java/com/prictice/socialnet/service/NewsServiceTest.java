package com.prictice.socialnet.service;

import com.prictice.socialnet.domain.News;
import com.prictice.socialnet.domain.PersonProfile;
import com.prictice.socialnet.dto.NewsDto;
import com.prictice.socialnet.utility.exception.NewsNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class NewsServiceTest {
    @Autowired
    private NewsService newsService;
    @Autowired
    private PersonProfileService profileService;

    public static final String NEWS_TEXT = "lorem ipsum blah blah blah";
    public static final String NEWS_UPDATE_TEXT = "blah blah blah";
    public static final String NEWS_PICTURE = "picture.jpeg";
    public static final Long PROFILE_ID = 4L;
    public PersonProfile personProfile;
    public News createNews = new News();
    public News updateNews = new News();

    @BeforeEach
    void setUp() {
        personProfile = profileService.findById(PROFILE_ID);

        createNews.setText(NEWS_TEXT);
        createNews.setPicture(NEWS_PICTURE);

        updateNews.setText(NEWS_UPDATE_TEXT);
        updateNews.setPicture(NEWS_PICTURE);
        updateNews.setPersonProfile(personProfile);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findAllByPersonProfileId() {
        List<NewsDto> test = newsService.findAllByPersonProfileId(personProfile.getId());
        assertNotNull(test);
    }

    @Test
    void createNews() {
        News newsTest = newsService.createNews(createNews, personProfile.getId());
        assertNotNull(newsTest.getId());
    }

    @Test
    void updateNews() {
        createNews.setPersonProfile(personProfile);
        News test = newsService.updateNews(createNews, updateNews);
        assertEquals(test.getText(), updateNews.getText());
    }

    @Test
    void deleteNews() {
        News newsTest = newsService.createNews(createNews, personProfile.getId());
        newsService.deleteNews(newsTest);
        assertThrows(
                NewsNotFoundException.class,
                () -> newsService.findOne(newsTest.getId()),
                "News was found!"
        );
    }
}