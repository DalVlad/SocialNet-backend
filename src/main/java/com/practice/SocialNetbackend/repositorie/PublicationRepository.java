package com.practice.SocialNetbackend.repositorie;

import com.practice.SocialNetbackend.model.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

public interface PublicationRepository extends JpaRepository<Publication, Long> {


    @Query("from Publication where ownCommunity.name = :communityName")
    List<Publication> findAllByCommunityName (@Param("communityName") String communityName);

    @Modifying
    @Transactional
    @Query("update Publication set memberRole.id = :roleId, message = :message where id = :publicationId")
    void updatePublication(@Param("roleId") Long roleId, @Param("message") String message, @Param("publicationId") Long publicationId);
}
