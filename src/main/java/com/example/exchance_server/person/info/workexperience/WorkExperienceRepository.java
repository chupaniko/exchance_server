package com.example.exchance_server.person.info.workexperience;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface WorkExperienceRepository extends JpaRepository<WorkExperience, Long> {
    @Override
    List<WorkExperience> findAll();
}
