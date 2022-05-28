package com.example.exchance_server.registration.token;

import com.example.exchance_server.appuser.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }

    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    public int setConfirmedAt(String token) {
        return confirmationTokenRepository.updateConfirmedAt(
                token, LocalDateTime.now());
    }

    public String findTokenByUser(AppUser user) {
        return confirmationTokenRepository.findTokenByUser(user.getId()).orElseThrow( () ->
        new IllegalStateException("no token found by user"));
    }

    public String findUnconfirmedTokenByUser(AppUser user) {
        return confirmationTokenRepository.findUnconfirmedTokenByUser(user.getId()).orElseThrow( () ->
                new IllegalStateException("no confirmed/unconfirmed token found by user"));
    }
}
