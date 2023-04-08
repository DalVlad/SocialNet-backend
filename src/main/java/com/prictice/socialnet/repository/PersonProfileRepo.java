package com.prictice.socialnet.repository;

import com.prictice.socialnet.domain.PersonProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonProfileRepo extends JpaRepository<PersonProfile, Long> {
}
