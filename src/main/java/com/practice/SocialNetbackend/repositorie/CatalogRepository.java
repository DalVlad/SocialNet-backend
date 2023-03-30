package com.practice.SocialNetbackend.repositorie;

import com.practice.SocialNetbackend.model.Catalog;
import com.practice.SocialNetbackend.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CatalogRepository extends JpaRepository<Catalog, Long> {

    @Query(value = "FROM Catalog c JOIN c.pathCatalogs pc WHERE pc.path = :path AND c.person = :person")
    Optional<Catalog> findByPathAndPerson(@Param("path") String path, @Param("person") Person person);

    Optional<Catalog> findByPerson(Person person);

}
