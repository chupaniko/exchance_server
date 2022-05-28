package com.example.exchance_server;

import com.example.exchance_server.admin.Manual;
import com.example.exchance_server.admin.ManualRepository;
import com.example.exchance_server.appuser.AppUser;
import com.example.exchance_server.appuser.AppUserRepository;
import com.example.exchance_server.appuser.AppUserRole;
import com.example.exchance_server.registration.token.ConfirmationToken;
import com.example.exchance_server.registration.token.ConfirmationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class StartupRunner implements CommandLineRunner {
    @Autowired
    AppUserRepository userRepository;
    @Autowired
    ConfirmationTokenRepository tokenRepository;
    @Autowired
    ManualRepository manualRepository;

    @Override
    public void run(String... args) throws Exception {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        AppUser user = new AppUser(
                "Nikolai",
                "chupaniko@ssau.ru",
                bCryptPasswordEncoder
                        .encode("n0n4cann0n"),
                AppUserRole.ADMIN,
                "",
                "",
                "+79276871196",
                "Samara",
                null
        );
        userRepository.save(user);
        ConfirmationToken token = new ConfirmationToken(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );
        token.setConfirmedAt(LocalDateTime.now().plusMinutes(1));
        tokenRepository.save(token);
        manualRepository.save(new Manual(
                "1.0",
                "<h2>Подраздел 1</h2>" +
                        "<p>Первый абзац подраздела 1</p>" +
                        "<p>Второй абзац подраздела 1</p>" +
                        "<h2>Подраздел 2</h2>" +
                        "<p>Первый абзац подраздела 2</p>" +
                        "<p>Второй абзац подраздела 2</p>",
                "Добавлены новые функции",
                "Николай Чупахин"
        ));
    }
}
