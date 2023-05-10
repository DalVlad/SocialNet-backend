package com.practice.SocialNetback.repository;

import com.practice.SocialNetback.models.Chat;
import com.practice.SocialNetback.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ChatsRepository extends JpaRepository<Chat, Long> {
    Optional<Chat> findByNameChat(String nameChat);

    //List<Chat> findByUsers(Set<User> users);

    List<Chat> findByNameChatContaining(String nameChat);

    Boolean existsByNameChat(String nameChat);
}
