package com.prictice.socialnet.repository;

import com.prictice.socialnet.domain.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepo extends JpaRepository<News, Long> {
    List<News> findAllByPersonProfileId(Long id);
}
