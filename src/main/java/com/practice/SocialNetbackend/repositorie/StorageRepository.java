package com.practice.SocialNetbackend.repositorie;

import com.practice.SocialNetbackend.model.Storage;
import com.practice.SocialNetbackend.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StorageRepository extends JpaRepository<Storage, Long> {

    Optional<Storage> findByPerson(Person person);

}
