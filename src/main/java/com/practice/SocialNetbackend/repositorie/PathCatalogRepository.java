package com.practice.SocialNetbackend.repositorie;

import com.practice.SocialNetbackend.model.Catalog;
import com.practice.SocialNetbackend.model.PathCatalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PathCatalogRepository extends JpaRepository<PathCatalog, Long> {

    Optional<PathCatalog> findByPathAndCatalog(String path, Catalog catalog);

}
