package com.practice.SocialNetbackend.repositorie;

import com.practice.SocialNetbackend.model.Storage;
import com.practice.SocialNetbackend.model.PathCatalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PathCatalogRepository extends JpaRepository<PathCatalog, Long> {

    Optional<PathCatalog> findByPathNameAndStorage(String path, Storage storage);

}
