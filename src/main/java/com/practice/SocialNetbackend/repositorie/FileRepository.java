package com.practice.SocialNetbackend.repositorie;

import com.practice.SocialNetbackend.model.File;
import com.practice.SocialNetbackend.model.PathCatalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {

    Optional<File> findByNameAndPathCatalog(String name, PathCatalog pathCatalog);

    long deleteByNameAndPathCatalog(String name, PathCatalog pathCatalog);

}
