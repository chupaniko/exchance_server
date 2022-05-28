package com.example.exchance_server.organization;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    @Override
    List<Organization> findAll();

    Optional<Organization> findByNumTaxpayer(String numTaxpayer);
}
