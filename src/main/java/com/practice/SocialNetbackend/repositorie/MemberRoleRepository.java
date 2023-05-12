package com.practice.SocialNetbackend.repositorie;

import com.practice.SocialNetbackend.model.MemberRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface MemberRoleRepository extends JpaRepository<MemberRole, Long> {

    @Transactional
    @Query("from MemberRole as mr inner join mr.members as m inner join m.community as c where m.person.id = :personID and c.name = :communityName")
    MemberRole getMemberRole(@Param("communityName") String communityName, @Param("personID") Long personID);
}
