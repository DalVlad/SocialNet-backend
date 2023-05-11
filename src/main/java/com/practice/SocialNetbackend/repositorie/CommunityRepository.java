package com.practice.SocialNetbackend.repositorie;

import com.practice.SocialNetbackend.model.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.Optional;

public interface CommunityRepository extends JpaRepository<Community, Long> {
    Optional<Community> findByName(String name);

    @Query("SELECT c.id FROM Community c where c.name = :name")
    Long findIdByName(@Param("name") String name);
}
