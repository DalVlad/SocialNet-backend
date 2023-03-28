package com.practice.SocialNetbackend.repositorie;

import com.practice.SocialNetbackend.model.Catalog;
import com.practice.SocialNetbackend.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CatalogRepository extends JpaRepository<Catalog, Long> {

    Optional<Catalog> findByPathAndPerson(String path, Person person);

}
