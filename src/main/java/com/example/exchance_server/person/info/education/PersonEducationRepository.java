package com.example.exchance_server.person.info.education;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface PersonEducationRepository extends JpaRepository<PersonEducation, Long> {
    List<PersonEducation> findAllByPerson(Long id);
}
