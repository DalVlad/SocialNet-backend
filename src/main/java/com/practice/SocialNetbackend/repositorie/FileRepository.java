package com.practice.SocialNetbackend.repositorie;

import com.practice.SocialNetbackend.dto.FileLikeDTO;
import com.practice.SocialNetbackend.model.File;
import com.practice.SocialNetbackend.model.PathCatalog;
import com.practice.SocialNetbackend.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {

    Optional<File> findByNameAndPathCatalog(String name, PathCatalog pathCatalog);

    long deleteByNameAndPathCatalog(String name, PathCatalog pathCatalog);

    @Query("select new com.practice.SocialNetbackend.dto.FileLikeDTO(" +
            "f.id, " +
            "count(fl), " +
            "sum(case when fl = :person then 1 else 0 end) > 0" +
            ") from File f left join f.likes fl where f = :file " +
            "group by f")
    Optional<FileLikeDTO> getFileLikes(@Param("person") Person person, @Param("file") File file);

}
