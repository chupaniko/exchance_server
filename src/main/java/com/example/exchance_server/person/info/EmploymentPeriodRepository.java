package com.example.exchance_server.person.info;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface EmploymentPeriodRepository extends JpaRepository<EmploymentPeriod, Long> {
    @Override
    List<EmploymentPeriod> findAll();
}
