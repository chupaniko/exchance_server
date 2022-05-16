package com.example.exchance_server.appuser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByEmail(String email);
    Optional<AppUser> findByToken(String token);

    @Transactional
    @Modifying
    @Query("UPDATE AppUser a " +
            "SET a.enabled = TRUE, a.token = :token WHERE a.email = :email")
    int enableAppUser(String email, String token);

    // ?
    /*@Transactional
    @Modifying
    @Query("select " +
    "")
    String[] buildAppUser(String email, String password, AppUserRole appUserRole);*/
}
