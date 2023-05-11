package com.practice.SocialNetbackend.repositorie;

import com.practice.SocialNetbackend.model.CommentOnPublication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface CommentOnPublicationRepository extends JpaRepository<CommentOnPublication, Long> {
    @Query("from CommentOnPublication where publication.id = :publicationID")
    List<CommentOnPublication> getComments(@Param("publicationID")Long publicationID);
}
