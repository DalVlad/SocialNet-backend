package com.practice.SocialNetbackend.repositorie;

import com.practice.SocialNetbackend.model.Member;
import com.practice.SocialNetbackend.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Modifying
    @Transactional
    @Query("delete from Member where person.id = :personId and community.id = :communityId")
    void deleteMember (@Param("personId") Long personId, @Param("communityId") Long communityId);

    @Modifying
    @Transactional
    @Query("update Member set memberRole.id = :memberRoleId where person.id = :personId and community.id = :communityId")
    void setNewRole(@Param("personId") Long personId, @Param("memberRoleId") Long memberRoleId, @Param("communityId") Long communityId);


    @Transactional
    List<Member> findAllByCommunity_Id(Long community_Id);

    @Query("from Person where login = :login")
    Person findPersonByLogin(@Param("login") String login);

}
