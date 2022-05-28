package com.example.exchance_server.registration.token;

import com.example.exchance_server.appuser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface ConfirmationTokenRepository
        extends JpaRepository<ConfirmationToken, Long> {

    Optional<ConfirmationToken> findByToken(String token);

    @Query("select t.token from ConfirmationToken t where t.appUser.id = :userId and t.confirmedAt is not null")
    Optional<String> findTokenByUser(Long userId);

    @Query("select t.token from ConfirmationToken t where t.appUser.id = :userId")
    Optional<String> findUnconfirmedTokenByUser(Long userId);

    /*Optional<String> findByAppUser(AppUser appUser);*/

    @Transactional
    @Modifying
    @Query("UPDATE ConfirmationToken c " +
            "SET c.confirmedAt = ?2 " +
            "WHERE c.token = ?1")
    int updateConfirmedAt(String token,
                          LocalDateTime confirmedAt);
}
