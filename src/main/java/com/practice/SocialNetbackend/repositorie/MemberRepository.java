package com.practice.SocialNetbackend.repositorie;

import com.practice.SocialNetbackend.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Modifying
    @Transactional
    @Query("delete from Member where client.id = :clientId and community.id = :communityId")
    void deleteMember (@Param("clientId") Long clientId, @Param("communityId") Long communityId);

    @Modifying
    @Transactional
    @Query("update Member set memberRole.id = :memberRoleId where client.id = :clientId and community.id = :communityId")
    void setNewRole(@Param("clientId") Long clientId, @Param("memberRoleId") Long memberRoleId, @Param("communityId") Long communityId);


    @Transactional
    List<Member> findAllByCommunity_Id(Long community_Id);

}
